package org.example.taskservice.dto;

import java.util.UUID;

public record Message(String text, UUID userId) {
}