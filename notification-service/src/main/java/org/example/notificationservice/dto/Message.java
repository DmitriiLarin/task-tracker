package org.example.notificationservice.dto;

import java.util.UUID;

public record Message(String text, UUID userId) {
}
