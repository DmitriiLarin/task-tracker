package org.example.taskservice.service;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(Message message) {
        kafkaTemplate.send("message_topic", message);
    }
}