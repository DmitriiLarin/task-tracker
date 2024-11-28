package org.example.dto.response;

import java.time.LocalDateTime;

public record MessageResponse(String text, LocalDateTime createdAt) {
}
