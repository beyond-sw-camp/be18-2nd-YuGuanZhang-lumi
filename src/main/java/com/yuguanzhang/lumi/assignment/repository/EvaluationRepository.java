package com.yuguanzhang.lumi.assignment.repository;

import com.yuguanzhang.lumi.assignment.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
