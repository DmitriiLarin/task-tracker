package org.example.dto.request;

import org.example.entity.Priority;

public record UpdateTaskPriorityRequest (int taskId, Priority priority){
}
