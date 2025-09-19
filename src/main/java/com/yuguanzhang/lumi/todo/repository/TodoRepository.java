package com.yuguanzhang.lumi.todo.repository;

import com.yuguanzhang.lumi.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser_UserIdAndDueDateBetween(UUID userId, LocalDate startDate,
                                                  LocalDate endDate);

    List<Todo> findByUser_UserIdAndDueDate(UUID userId, LocalDate dueDate);
}
