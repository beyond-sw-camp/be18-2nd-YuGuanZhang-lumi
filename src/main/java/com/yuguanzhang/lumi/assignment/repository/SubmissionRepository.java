package com.yuguanzhang.lumi.assignment.repository;

import com.yuguanzhang.lumi.assignment.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
