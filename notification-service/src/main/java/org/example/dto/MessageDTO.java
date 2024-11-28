package org.example.dto;

import java.util.UUID;

public record MessageDTO(EventType eventType, UUID userId, String boardName) {
}
