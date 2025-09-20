package com.yuguanzhang.lumi.course.respository;

import com.yuguanzhang.lumi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByChannelUserUserUserIdAndStartDateBetweenOrderByStartDateAsc(UUID userId,
                                                                                   LocalDateTime startDate,
                                                                                   LocalDateTime endDate);

    List<Course> findByChannelUser_Channel_ChannelIdInAndStartDateBetweenOrderByStartDateAsc(
            List<Long> channelIds, LocalDateTime startDate, LocalDateTime endDate);

}
