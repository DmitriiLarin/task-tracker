package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.entity.Message;
import org.example.notificationservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }
}
