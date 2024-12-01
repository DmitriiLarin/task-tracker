package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Message;
import org.example.entity.TaskNotification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskSchedulerService {

    private final TaskNotificationService taskNotificationService;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 */4 * * *")
    public void scheduleFileCleanup() {
        log.info("Cron work");
        LocalDateTime now = LocalDateTime.now();
        List<TaskNotification> list = taskNotificationService.getAllTaskNotificationsInRange(now, now.plusDays(2));
        for (TaskNotification taskNotification : list) {
            Message message = new Message();
            Duration duration = Duration.between(now, taskNotification.getDeadline());
            long hours = duration.toHours();
            message.setText("До конца задачи - " + taskNotification.getTaskName()
                    + " с доски -" + taskNotification.getBoardName() + " осталось: " +
                    hours + " часов");
            message.setCreatedAt(LocalDateTime.now());
            message.setUserId(taskNotification.getUserId());
            messageService.save(message);
        }
    }
}