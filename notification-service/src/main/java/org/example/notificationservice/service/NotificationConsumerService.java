package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.dto.MessageDTO;
import org.example.notificationservice.entity.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumerService {

    @KafkaListener(topics = "message_topic", groupId = "my-group")
    public void listenNotifications(MessageDTO message) {
        System.out.println("34598340598304958093548" + message.text());
//        switch (message.eventType()) {
//            case CATEGORY_ADDED -> handleCategoryNotification(message);
//            case GOAL_ADDED -> handleGoalNotification(message);
//            case LIMIT_ADDED -> handleLimitNotification(message);
//            case PAYMENT_ADDED -> handlePaymentNotification(message);
//            case TRANSACTION_ADDED -> handleTransactionNotification(message);
//            default -> System.out.println("Получено неизвестное уведомление");
//        }
    }
}
