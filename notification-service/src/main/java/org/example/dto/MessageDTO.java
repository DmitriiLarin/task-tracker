package org.example.dto;

import org.example.entity.EventType;

import java.util.UUID;

public record MessageDTO(EventType eventType, UUID userId, String boardName) {
}
