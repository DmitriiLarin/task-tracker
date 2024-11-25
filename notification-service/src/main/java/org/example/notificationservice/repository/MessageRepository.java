package org.example.notificationservice.repository;

import org.example.notificationservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
