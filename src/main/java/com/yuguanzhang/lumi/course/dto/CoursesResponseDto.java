package com.yuguanzhang.lumi.course.dto;

import com.yuguanzhang.lumi.course.entity.Course;
import com.yuguanzhang.lumi.course.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CoursesResponseDto {
    private final Long courseId;
    private final Long channelId;
    private final String channelName;
    private final StatusType statusType;

    public static CoursesResponseDto fromEntity(Course course) {
        return CoursesResponseDto.builder()
                                 .courseId(course.getCourseId())
                                 .channelId(course.getChannelUser()
                                                  .getChannel()
                                                  .getChannelId())
                                 .channelName(course.getChannelUser()
                                                    .getChannel()
                                                    .getName())
                                 .statusType(course.getStatusType())
                                 .build();
    }
}
