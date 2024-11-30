package org.example.dto;


import org.example.entity.EventType;
import org.example.entity.TaskType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskNotificationDTO(String taskName, String boardName, UUID userId,
                                  EventType eventType, TaskType taskType, int taskId,
                                  LocalDateTime deadline) {}