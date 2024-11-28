package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Message;
import org.example.entity.TaskNotification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskSchedulerService {

    private final TaskNotificationService taskNotificationService;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 */4 * * *")
    public void scheduleFileCleanup() {
        LocalDateTime now = LocalDateTime.now();
        List<TaskNotification> list = taskNotificationService.getAllTaskNotificationsInRange(now, now.plusDays(2));
        for (TaskNotification taskNotification : list) {
            Message message = new Message();
            Duration duration = Duration.between(now, taskNotification.getDeadline());
            long hours = duration.toHours();
            message.setText("До конца задачи - " + taskNotification.getTaskName()
                    + " с доски -" + taskNotification.getBoardName() + "осталось: " +
                    hours + " часов");
            message.setUserId(taskNotification.getUserId());
            messageService.save(message);
        }
    }
}
