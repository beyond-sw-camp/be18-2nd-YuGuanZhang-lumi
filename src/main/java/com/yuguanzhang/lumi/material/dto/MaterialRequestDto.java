package com.yuguanzhang.lumi.material.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MaterialRequestDto {

    private String title;

    private String content;

    private List<Long> fileIds;

}
