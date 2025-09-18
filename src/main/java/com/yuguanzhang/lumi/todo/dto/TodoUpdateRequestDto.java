package com.yuguanzhang.lumi.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TodoUpdateRequestDto {
    private String description;
    private Boolean status;
}
