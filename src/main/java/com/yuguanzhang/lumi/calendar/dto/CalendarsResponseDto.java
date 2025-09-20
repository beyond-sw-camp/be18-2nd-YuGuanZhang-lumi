package com.yuguanzhang.lumi.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuguanzhang.lumi.course.entity.Course;
import com.yuguanzhang.lumi.role.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CalendarsResponseDto {
    private final Long channelId;
    private final String channelName;
    private final Long entityId;
    private final String entityType;
    private final RoleName roleName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endDate;

    public static CalendarsResponseDto fromCourse(Course course) {
        return CalendarsResponseDto.builder()
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
                                   .startDate(course.getStartDate())
                                   .endDate(course.getEndDate())
                                   .build();
    }

    // public static CalendarsResponseDto fromAssignment(Assignment assignment) {
    //     return CalendarsResponseDto.builder().build();
    // }

    // public static CalendarsResponseDto fromEvaludation(Assignment assignment) {
    //     return CalendarsResponseDto.builder().build();
    // }
}
