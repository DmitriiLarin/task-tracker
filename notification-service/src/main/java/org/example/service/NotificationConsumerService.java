package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageDTO;
import org.example.entity.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {
    private final MessageService messageService;

    @KafkaListener(topics = "message_topic", groupId = "my-group")
    public void listenNotifications(MessageDTO messageDTO) {
        log.info("Send message {}", messageDTO);
        switch (messageDTO.eventType()) {
            case BOARD_ADD -> handleMessageAddedBoard(messageDTO);
            default -> log.error("Unknown event type {}", messageDTO.eventType());
        }
    }

    private void handleMessageAddedBoard(MessageDTO messageDTO) {
        Message message = new Message();
        message.setUserId(messageDTO.userId());
        message.setCreatedAt(LocalDateTime.now());
        message.setText("Вас добавили на доску " + messageDTO.boardName());
        messageService.save(message);
    }
}
