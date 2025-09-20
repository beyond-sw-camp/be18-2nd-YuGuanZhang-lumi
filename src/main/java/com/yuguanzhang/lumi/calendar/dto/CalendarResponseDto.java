package com.yuguanzhang.lumi.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuguanzhang.lumi.course.entity.Course;
import com.yuguanzhang.lumi.course.enums.StatusType;
import com.yuguanzhang.lumi.role.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CalendarResponseDto {
    private final Long channelId;
    private final String channelName;
    private final Long entityId;
    private final String entityType;
    private final RoleName roleName;

    // Course
    private String location;
    private StatusType statusType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endDate;

    // ASSIGNMENT
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadlineAt;
    private Boolean isSubmission;

    // EVALUATION
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime evaluationDeadlineAt;
    private Boolean isEvaluation;

    public static CalendarResponseDto fromCourse(Course course) {
        return CalendarResponseDto.builder()
                                  .channelId(course.getChannelUser()
                                                   .getChannel()
                                                   .getChannelId())
                                  .channelName(course.getChannelUser()
                                                     .getChannel()
                                                     .getName())
                                  .entityId(course.getCourseId())
                                  .entityType("COURSE")
                                  .roleName(course.getChannelUser()
                                                  .getRole()
                                                  .getRoleName())
                                  .location(course.getLocation())
                                  .statusType(course.getStatusType())
                                  .startDate(course.getStartDate())
                                  .endDate(course.getEndDate())
                                  .build();
    }

    // 과제 제출
    // public static CalendarResponseDto fromAssignment(Assignment assignment) {
    //     return CalendarResponseDto.builder()
    //                               .channelId(assignment.getChannelUser()
    //                                                .getChannel()
    //                                                .getChannelId())
    //                               .channelName(assignment.getChannelUser()
    //                                                  .getChannel()
    //                                                  .getName())
    //                               .entityId(assignment.getCourseId())
    //                               .entityType("ASSIGNMENT")
    //                               .roleName(assignment.getChannelUser()
    //                                               .getRole()
    //                                               .getRoleName())
    //                               .deadlineAt()
    //                               .isSubmission(assignment.)
    //                               .build();
    // }

    // 과제 평가
    // public static CalendarResponseDto fromEvaluation(Assignment assignment) {
    //     return CalendarResponseDto.builder()
    //                               .channelId(assignment.getChannelUser()
    //                                                .getChannel()
    //                                                .getChannelId())
    //                               .channelName(assignment.getChannelUser()
    //                                                  .getChannel()
    //                                                  .getName())
    //                               .entityId(assignment.getCourseId())
    //                               .entityType("ASSIGNMENT")
    //                               .roleName(assignment.getChannelUser()
    //                                               .getRole()
    //                                               .getRoleName())
    //                               .evaluationDeadlineAt()
    //                               .isEvaluation(assignment.)
    //                               .build();
    // }
}
