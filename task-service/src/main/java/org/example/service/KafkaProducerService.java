package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.MessageDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public void sendMessage(MessageDTO message) {
        kafkaTemplate.send("message_topic", message);
    }
}