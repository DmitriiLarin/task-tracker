package org.example.notificationservice.repository;

import org.example.notificationservice.entity.TaskNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Integer> {
    List<TaskNotification> findAllByDeadlineBeforeAndDeadlineAfter(LocalDateTime start, LocalDateTime end);
}
