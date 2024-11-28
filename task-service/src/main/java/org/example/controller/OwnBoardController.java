package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.UserClient;
import org.example.dto.BoardUserDTO;
import org.example.dto.OwnBoardDTO;
import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.dto.request.AddBoardRequest;
import org.example.dto.response.OwnBoardResponse;
import org.example.entity.OwnBoard;
import org.example.entity.OwnTask;
import org.example.entity.TaskType;
import org.example.service.BoardUserService;
import org.example.service.OwnBoardService;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/own-board")
@RequiredArgsConstructor
@Slf4j
public class OwnBoardController {
    private final UserClient userClient;
    private final OwnBoardService ownBoardService;
    public final BoardUserService boardUserService;
    public final TaskService taskService;
    private final static String TOKEN = "Authorization";

    @PostMapping("/add")
    public void createBoard(@RequestHeader(TOKEN) String token, @RequestBody AddBoardRequest request) {
        UUID user_id = userClient.getUserByToken(token);
        OwnBoard ownBoard = ownBoardService.addBoard(new OwnBoardDTO(user_id));
        BoardUserDTO boardUserDTO = new BoardUserDTO(ownBoard.getId(), user_id);
        boardUserService.save(boardUserDTO);
    }

    @GetMapping()
    public OwnBoardResponse getSelfBoard(@RequestHeader(TOKEN) String token){
        UUID user_id = userClient.getUserByToken(token);

        if (!ownBoardService.existsByOwnerId(user_id)) {
            ownBoardService.addBoard(new OwnBoardDTO(user_id));
            return new OwnBoardResponse("Моя таблица задач", new ArrayList<>());
        }

        int id = ownBoardService.getIdByUserId(user_id);
        String s = initials(token, user_id);
        List<OwnTask> ownTasks= ownBoardService.findTasksByBoardId(id);

        List<TaskDTO> ownTaskDTOS = ownTasks.stream().map(task ->
                        new TaskDTO(task.getId(), task.getName(), task.getPriority(),
                                task.getTimer(), s, task.getDescription(),  TaskType.OWN_TASK))
                .collect(Collectors.toCollection(ArrayList::new));

        ownTaskDTOS.addAll(taskService.findTasksByUser_id(user_id).stream().map(task ->
                        new TaskDTO(task.getId(), task.getName(), task.getPriority(),
                                task.getTimer(), s, task.getDescription(), TaskType.TASK))
                .collect(Collectors.toCollection(ArrayList::new)));

        return new OwnBoardResponse( "Моя таблица задач", ownTaskDTOS);
    }

    private String initials(String token, UUID user_id) {
        UserDTO userDTO = userClient.getUserData(token, user_id);
        return userDTO.surname().charAt(0) + "" + userDTO.name().charAt(0);
    }
}
