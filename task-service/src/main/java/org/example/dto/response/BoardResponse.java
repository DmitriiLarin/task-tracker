package org.example.dto.response;

import org.example.dto.TaskDTO;

import java.util.List;

public record BoardResponse(int id, String name, List<TaskDTO> tasks) {
}
