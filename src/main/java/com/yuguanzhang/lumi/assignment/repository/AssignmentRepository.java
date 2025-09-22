package com.yuguanzhang.lumi.assignment.repository;

import com.yuguanzhang.lumi.assignment.entity.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Page<Assignment> findByChannelUser_Channel_ChannelId(Long channelId, Pageable pageable);
}
