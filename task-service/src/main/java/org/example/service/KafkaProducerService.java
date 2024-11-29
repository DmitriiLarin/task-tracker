package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.MessageDTO;
import org.example.dto.TaskDTO;
import org.example.dto.TaskNotificationDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, MessageDTO> kafkaTemplateMessage;
    private final KafkaTemplate<String, TaskNotificationDTO> kafkaTemplateTask;

    public void sendMessage(MessageDTO message) {
        kafkaTemplateMessage.send("message_topic", message);
    }

    public void sendTask(TaskNotificationDTO taskNotificationDTO) {
        kafkaTemplateTask.send("taskNotification-topic", taskNotificationDTO);
    }
}