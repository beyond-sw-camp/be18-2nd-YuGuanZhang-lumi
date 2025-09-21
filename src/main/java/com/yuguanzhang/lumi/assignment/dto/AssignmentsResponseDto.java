package com.yuguanzhang.lumi.assignment.dto;

import com.yuguanzhang.lumi.assignment.entity.Assignment;
import com.yuguanzhang.lumi.file.dto.FileUploadResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class AssignmentsResponseDto {

    private final Long assignmentId;

    private final String title;

    private final String content;

    private final LocalDate deadlineAt;

    private final boolean isEvaluation;

    private final boolean isSubmission;

    private final LocalDate evaluationDeadlineAt;

    public static AssignmentsResponseDto fromEntity(Assignment assignment) {
        return AssignmentsResponseDto.builder()
                                     .assignmentId(assignment.getAssignmentId())
                                     .title(assignment.getTitle())
                                     .content(assignment.getContent())
                                     .deadlineAt(assignment.getDeadlineAt())
                                     .isEvaluation(assignment.isEvaluation())
                                     .isSubmission(assignment.isSubmission())
                                     .evaluationDeadlineAt(assignment.getEvaluationDeadlineAt())
                                     .build();
    }
}
