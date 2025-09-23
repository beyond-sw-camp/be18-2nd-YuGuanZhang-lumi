package com.yuguanzhang.lumi.todo.dto;

import com.yuguanzhang.lumi.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class TodosResponseDto {
    private final int incompleteCount;
    private final boolean allCompleted;
    private final LocalDate dueDate;

    public static TodosResponseDto fromEntity(int incompleteCount, boolean allCompleted,
                                              Todo todo) {
        return TodosResponseDto.builder()
                               .incompleteCount((int) incompleteCount)
                               .allCompleted(allCompleted)
                               .dueDate(todo.getDueDate())
                               .build();
    }
}
