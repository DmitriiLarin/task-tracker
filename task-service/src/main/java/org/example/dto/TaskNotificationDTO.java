package org.example.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record TaskNotificationDTO(String taskName, String boardName, UUID userId,
                                  EventType eventType, int boardId, int taskId,
                                  LocalDateTime deadline) {}