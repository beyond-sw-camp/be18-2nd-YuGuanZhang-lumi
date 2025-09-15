package com.yuguanzhang.lumi.todo.dto;

import com.yuguanzhang.lumi.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class TodosResponseDto {
    private final int incompleteCount;
    private final boolean allCompleted;

    public static TodosResponseDto fromEntity(List<Todo> todos) {
        long incompleteCount = todos.stream().filter(todo -> !todo.getStatus()).count();
        boolean allCompleted = incompleteCount == 0;
        return TodosResponseDto.builder().incompleteCount((int) incompleteCount)
                               .allCompleted(allCompleted).build();
    }
}
