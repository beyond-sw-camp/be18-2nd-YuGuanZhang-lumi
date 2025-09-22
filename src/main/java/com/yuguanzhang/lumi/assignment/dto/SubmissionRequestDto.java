package com.yuguanzhang.lumi.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class SubmissionRequestDto {

    private String title;
    private String description;
    private List<Long> fileIds;
}
