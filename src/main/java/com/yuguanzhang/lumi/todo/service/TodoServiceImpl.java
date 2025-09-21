package com.yuguanzhang.lumi.todo.service;

import com.yuguanzhang.lumi.common.exception.GlobalException;
import com.yuguanzhang.lumi.common.exception.message.ExceptionMessage;
import com.yuguanzhang.lumi.todo.dto.TodoRequestDto;
import com.yuguanzhang.lumi.todo.dto.TodoResponseDto;
import com.yuguanzhang.lumi.todo.dto.TodoUpdateRequestDto;
import com.yuguanzhang.lumi.todo.dto.TodosResponseDto;
import com.yuguanzhang.lumi.todo.entity.Todo;
import com.yuguanzhang.lumi.todo.repository.TodoRepository;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, TodosResponseDto> getTodos(UUID userId, LocalDate startDate,
                                                     LocalDate endDate) {

        if (startDate == null) {
            startDate = LocalDate.now()
                                 .withDayOfMonth(1);
        }

        log.info("startDate:{}", startDate);

        if (endDate == null) {
            endDate = startDate.withDayOfMonth((startDate.lengthOfMonth()));
        }

        List<Todo> todos =
                todoRepository.findByUser_UserIdAndDueDateBetween(userId, startDate, endDate);

        return todos.stream()
                    .collect(Collectors.groupingBy(Todo::getDueDate, TreeMap::new,
                                                   // 날짜별로 그룹화, 날짜 오름차순 정렬
                                                   Collectors.collectingAndThen(Collectors.toList(),
                                                                                // 같은 날짜의 Todo를 List로 모음
                                                                                TodosResponseDto::fromEntity)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponseDto> getTodosByDate(UUID userId, LocalDate dueDate) {

        List<Todo> todos = todoRepository.findByUser_UserIdAndDueDate(userId, dueDate);

        return todos.stream()
                    .map(TodoResponseDto::fromEntity)
                    .toList();
    }

    @Override
    @Transactional
    public TodoResponseDto createTodo(UUID userId, TodoRequestDto request) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new GlobalException(
                                          ExceptionMessage.USER_NOT_FOUND));

        Todo todo = request.toEntity(user);
        Todo saved = todoRepository.save(todo);

        return TodoResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public TodoResponseDto updateTodo(UUID userId, TodoUpdateRequestDto request, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                                  .orElseThrow(() -> new GlobalException(
                                          ExceptionMessage.TODO_NOT_FOUND));

        if (!todo.getUser()
                 .getUserId()
                 .equals(userId)) {
            throw new GlobalException(ExceptionMessage.UNAUTHORIZED_TODO_UPDATE);
        }

        if (request.getDescription() != null) {
            todo.updateDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            todo.updateStatus(request.getStatus());
        }

        Todo saved = todoRepository.save(todo);

        return TodoResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public void deleteTodo(UUID userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                                  .orElseThrow(() -> new GlobalException(
                                          ExceptionMessage.TODO_NOT_FOUND));

        if (!todo.getUser()
                 .getUserId()
                 .equals(userId)) {
            throw new GlobalException(ExceptionMessage.UNAUTHORIZED_TODO_DELETE);
        }

        todoRepository.delete(todo);
    }


}
