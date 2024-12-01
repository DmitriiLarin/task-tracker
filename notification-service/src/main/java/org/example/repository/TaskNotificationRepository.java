package org.example.repository;

import org.example.entity.TaskNotification;
import org.example.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Integer> {
    List<TaskNotification> findAllByDeadlineBeforeAndDeadlineAfter(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM TaskNotification t WHERE t.taskId = :taskId AND t.taskType = :taskType")
    TaskNotification findByTaskIdAndTaskType(@Param("taskId") int taskId, @Param("taskType") TaskType taskType);

}
