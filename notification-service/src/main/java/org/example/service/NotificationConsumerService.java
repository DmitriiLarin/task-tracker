package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageDTO;
import org.example.dto.TaskNotificationDTO;
import org.example.entity.Message;
import org.example.entity.TaskNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {
    private final MessageService messageService;
    private final TaskNotificationService taskNotificationService;

    @KafkaListener(topics = "message_topic", groupId = "my-group")
    public void listenNotifications(MessageDTO messageDTO) {
        log.info("Send message {}", messageDTO);
        switch (messageDTO.eventType()) {
            case BOARD_ADD -> handleMessageAddedBoard(messageDTO);
            default -> log.error("Unknown event type {}", messageDTO.eventType());
        }
    }

    @KafkaListener(topics = "taskNotification-topic", groupId = "my-group")
    public void listenTaskNotifications(TaskNotificationDTO taskNotificationDTO) {
        log.info("Send message {}", taskNotificationDTO);
        switch (taskNotificationDTO.eventType()) {
            case TASK_ADD -> handleTaskNotificationAdd(taskNotificationDTO);
            case TASK_UPDATE -> handleTaskNotificationUpdate(taskNotificationDTO);
            case TASK_DELETE -> handleTaskNotificationDelete(taskNotificationDTO);
            default -> log.error("Unknown event type {}", taskNotificationDTO.eventType());
        }
    }

    private void handleMessageAddedBoard(MessageDTO messageDTO) {
        Message message = new Message();
        message.setUserId(messageDTO.userId());
        message.setCreatedAt(LocalDateTime.now());
        message.setText("Вас добавили на доску " + messageDTO.boardName());
        messageService.save(message);
    }

    private void handleTaskNotificationAdd(TaskNotificationDTO taskNotificationDTO) {
        TaskNotification taskNotification = new TaskNotification();

        taskNotification.setUserId(taskNotificationDTO.userId());
        taskNotification.setTaskId(taskNotificationDTO.taskId());
        taskNotification.setTaskType(taskNotificationDTO.taskType());
        taskNotification.setCreatedAt(LocalDateTime.now());
        taskNotification.setTaskName(taskNotificationDTO.taskName());
        taskNotification.setBoardName(taskNotificationDTO.boardName());
        taskNotification.setDeadline(taskNotificationDTO.deadline());

        taskNotificationService.save(taskNotification);
    }

    private void handleTaskNotificationUpdate(TaskNotificationDTO taskNotificationDTO) {
        taskNotificationService.update(taskNotificationDTO);
    }

    private void handleTaskNotificationDelete(TaskNotificationDTO taskNotificationDTO) {
        Message message = new Message();

        message.setText("Задача " + taskNotificationDTO.taskName()
                + " удалена, с доски" + taskNotificationDTO.boardName());

        taskNotificationService.delete(taskNotificationDTO);
    }
}
