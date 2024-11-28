package org.example.dto.request;

import org.example.entity.Priority;

import java.time.LocalDateTime;

public record AddTaskRequest(String name, String description, Priority priority, LocalDateTime deadline, String email, int boardId) {
}
