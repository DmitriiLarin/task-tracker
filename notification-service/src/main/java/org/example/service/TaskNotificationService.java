package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TaskNotificationDTO;
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

    public void save(TaskNotification taskNotification) {
        taskNotificationRepository.save(taskNotification);
    }

    public void update(TaskNotificationDTO taskNotificationDTO) {
        TaskNotification taskNotification = taskNotificationRepository.
                findByTaskIdAndBoardId(taskNotificationDTO.taskId(), taskNotificationDTO.boardId());

        taskNotification.setDeadline(taskNotificationDTO.deadline());
        taskNotification.setTaskName(taskNotificationDTO.taskName());
        taskNotification.setBoardName(taskNotificationDTO.boardName());

        taskNotificationRepository.save(taskNotification);
    }
}
