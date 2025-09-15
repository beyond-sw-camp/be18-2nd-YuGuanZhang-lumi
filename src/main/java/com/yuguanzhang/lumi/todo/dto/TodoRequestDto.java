package com.yuguanzhang.lumi.todo.dto;

import com.yuguanzhang.lumi.todo.entity.Todo;
import com.yuguanzhang.lumi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class TodoRequestDto {
    private final String description;
    private final LocalDate dueDate;

    public static Todo toEntity(User user, TodoRequestDto request) {
        return Todo.builder().description(request.getDescription()).status(false).user(user)
                   .dueDate(request.getDueDate()).build();
    }
}
