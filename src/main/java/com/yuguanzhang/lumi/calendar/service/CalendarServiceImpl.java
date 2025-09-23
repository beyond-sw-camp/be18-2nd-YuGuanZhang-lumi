package com.yuguanzhang.lumi.calendar.service;

import com.yuguanzhang.lumi.assignment.entity.Assignment;
import com.yuguanzhang.lumi.assignment.repository.AssignmentRepository;
import com.yuguanzhang.lumi.calendar.dto.CalendarResponseDto;
import com.yuguanzhang.lumi.calendar.dto.CalendarsResponseDto;
import com.yuguanzhang.lumi.channel.repository.ChannelUserRepository;
import com.yuguanzhang.lumi.common.util.DateRangeUtil;
import com.yuguanzhang.lumi.common.util.DateTimeUtil;
import com.yuguanzhang.lumi.common.util.LocalDateRange;
import com.yuguanzhang.lumi.course.entity.Course;
import com.yuguanzhang.lumi.course.respository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {
    private final ChannelUserRepository channelUserRepository;
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarsResponseDto> getCalendars(UUID userId, LocalDate startDate,
                                                   LocalDate endDate) {

        LocalDateRange range = DateRangeUtil.getMonthlyRange(startDate, endDate);
        LocalDateTime startDateTime = range.getStartDateTime();
        LocalDateTime endDateTime = range.getEndDateTime();


        List<Long> channelIds = channelUserRepository.findChannelIdsByUserId(userId);

        List<Course> courses =
                courseRepository.findCoursesInDateRange(channelIds, startDateTime, endDateTime);
        List<Assignment> assignments =
                assignmentRepository.findAssignmentsInDateRange(channelIds, startDateTime,
                                                                endDateTime);


        List<CalendarsResponseDto> responseDtos = new ArrayList<>();

        courses.forEach(course -> responseDtos.add(CalendarsResponseDto.fromCourse(course)));
        assignments.forEach((assignment -> {
            responseDtos.add(CalendarsResponseDto.fromAssignment(assignment));

            if (assignment.isEvaluation()) {
                responseDtos.add(CalendarsResponseDto.fromEvaluation(assignment));
            }
        }));

        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarResponseDto> getCalendarsByDate(UUID userId, LocalDate dueDate) {
        LocalDateTime startDateTime = DateTimeUtil.getStartOfDay(dueDate);
        LocalDateTime endDateTime = DateTimeUtil.getEndOfDay(dueDate);


        List<Long> channelIds = channelUserRepository.findChannelIdsByUserId(userId);

        List<Course> courses =
                courseRepository.findCoursesInDateRange(channelIds, startDateTime, endDateTime);
        List<Assignment> assignments =
                assignmentRepository.findAssignmentsInDateRange(channelIds, startDateTime,
                                                                endDateTime);

        // 과제 제출 마감, 평가 마감 추가
        List<CalendarResponseDto> responseDtos = new ArrayList<>();

        courses.forEach(course -> responseDtos.add(CalendarResponseDto.fromCourse(course)));
        assignments.forEach(assignment -> {
            responseDtos.add(CalendarResponseDto.fromAssignment(assignment));
            if (assignment.isEvaluation()) {
                responseDtos.add(CalendarResponseDto.fromEvaluation(assignment));
            }
        });

        return responseDtos;
    }
}
