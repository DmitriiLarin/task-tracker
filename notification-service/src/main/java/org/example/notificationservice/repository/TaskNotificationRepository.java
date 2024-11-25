package org.example.notificationservice.repository;

import org.example.notificationservice.entity.TaskNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskNotificationMessage extends JpaRepository<TaskNotification, Integer> {
}
