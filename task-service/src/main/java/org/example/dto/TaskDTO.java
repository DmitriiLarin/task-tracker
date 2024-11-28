package org.example.dto;

import org.example.entity.Priority;
import org.example.entity.TaskType;

import java.time.LocalDateTime;

public record TaskDTO(int id, String name, Priority priority, LocalDateTime timer, String owner, String description, TaskType type) {}
