package com.yuguanzhang.lumi.todo.service;

import com.yuguanzhang.lumi.todo.dto.TodoRequestDto;
import com.yuguanzhang.lumi.todo.dto.TodoResponseDto;
import com.yuguanzhang.lumi.todo.dto.TodoUpdateRequestDto;
import com.yuguanzhang.lumi.todo.dto.TodosResponseDto;
import com.yuguanzhang.lumi.todo.entity.Todo;
import com.yuguanzhang.lumi.todo.repository.TodoRepository;
import com.yuguanzhang.lumi.user.entity.User;
import com.yuguanzhang.lumi.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
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

        List<Todo> todos = todoRepository.findByUserIdAndMonthRange(userId, startDate, endDate);

        return todos.stream()
                    .collect(Collectors.groupingBy(Todo::getDueDate, // 날짜별로 그루핑
                                                   Collectors.collectingAndThen(Collectors.toList(),
                                                                                // 같은 날짜의 Todo를 List로 모음
                                                                                TodosResponseDto::fromEntity))); // 해당 List를 Dto로 변경
    }

    @Override
    public List<TodoResponseDto> getTodosByDate(UUID userId, LocalDate dueDate) {

        List<Todo> todos = todoRepository.findByUser_UserIdAndDueDate(userId, dueDate);

        return todos.stream()
                    .map(TodoResponseDto::fromEntity)
                    .toList();
    }

    @Override
    public TodoResponseDto createTodo(UUID userId, TodoRequestDto request) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(
                                          () -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Todo todo = request.toEntity(user);
        Todo saved = todoRepository.save(todo);

        return TodoResponseDto.fromEntity(saved);
    }

    @Override
    public TodoResponseDto updateTodo(UUID userId, TodoUpdateRequestDto request, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                                  .orElseThrow(
                                          () -> new EntityNotFoundException("해당 투두를 찾을 수 없습니다."));

        if (!todo.getUser()
                 .getUserId()
                 .equals(userId)) {
            throw new IllegalArgumentException("해당 투두를 수정할 권한이 없습니다.");
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
    public void deleteTodo(UUID userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                                  .orElseThrow(
                                          () -> new EntityNotFoundException("해당 투두를 찾을 수 없습니다."));

        if (!todo.getUser()
                 .getUserId()
                 .equals(userId)) {
            throw new IllegalArgumentException("해당 투두를 삭제할 권한이 없습니다.");
        }

        todoRepository.delete(todo);
    }


}
