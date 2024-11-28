package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.TaskNotification;
import org.example.repository.TaskNotificationRepository;
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
