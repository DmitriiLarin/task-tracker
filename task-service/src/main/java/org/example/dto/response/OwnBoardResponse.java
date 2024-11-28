package org.example.dto.response;

import org.example.dto.TaskDTO;

import java.util.List;

public record OwnBoardResponse(String name, List<TaskDTO> tasks){
}
