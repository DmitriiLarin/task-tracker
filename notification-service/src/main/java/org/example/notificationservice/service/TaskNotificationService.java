package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.entity.TaskNotification;
import org.example.notificationservice.repository.TaskNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskNotificationService {
    private final TaskNotificationRepository taskNotificationRepository;

    public List<TaskNotification> getAllTaskNotificationsInRange(LocalDateTime start, LocalDateTime end) {
        return taskNotificationRepository.findAllByDeadlineBeforeAndDeadlineAfter(start, end);
    }
}
