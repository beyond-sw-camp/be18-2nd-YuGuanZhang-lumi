package com.yuguanzhang.lumi.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class AssignmentRequestDto {

    private String title;

    private String content;

    private LocalDate deadlineAt;

    private boolean isEvaluation;

    private boolean isSubmission;

    private List<Long> fileIds;

}
