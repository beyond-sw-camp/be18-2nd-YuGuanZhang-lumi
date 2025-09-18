package com.yuguanzhang.lumi.todo.repository;

import com.yuguanzhang.lumi.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE t.user.userId = :userId AND t.dueDate BETWEEN :startDate AND :endDate")
    List<Todo> findByUserIdAndMonthRange(UUID userId, LocalDate startDate, LocalDate endDate);

    List<Todo> findByUser_UserIdAndDueDate(UUID userId, LocalDate dueDate);
}
