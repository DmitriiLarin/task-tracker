package org.example.notificationservice.dto;

import java.util.UUID;

public record MessageDTO(String text, UUID userId) {
}
