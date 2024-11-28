package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.UserClient;
import org.example.dto.request.AddTaskRequest;
import org.example.dto.request.UpdateTaskPriorityRequest;
import org.example.dto.request.UpdateTaskRequest;
import org.example.entity.Board;
import org.example.entity.Task;
import org.example.service.BoardService;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final UserClient userClient;
    private final TaskService taskService;
    private final BoardService boardService;
    private final static String TOKEN = "Authorization";

    @PostMapping("update-priority")
    public Task getUserTasks(@RequestHeader(TOKEN) String token,
                             @RequestBody UpdateTaskPriorityRequest updateTaskPriorityRequest) {
        return taskService.updatePriority(updateTaskPriorityRequest);
    }

    @PostMapping("/add")
    public Task createBoard(@RequestHeader(TOKEN) String token, @RequestBody AddTaskRequest request) {
        Task task = new Task();
        Board board = boardService.getByBoardId(request.boardId());
        if (request.email() != null) {
            UUID userid = userClient.getUserEmail(token, request.email());
            task.setUserId(userid);
        }
        else{
            task.setUserId(null);
        }

        task.setCreatedAt(LocalDateTime.now());
        task.setBoard(board);
        task.setName(request.name());
        task.setPriority(request.priority());
        task.setDescription(request.description());
        task.setTimer(request.deadline());

        taskService.createTask(task);
        return task;
    }

    @PutMapping("/update/{taskId}")
    public Task updateTask(@RequestHeader(TOKEN) String token, @PathVariable("taskId") int taskId, @RequestBody UpdateTaskRequest updateTaskRequest) {
        Task task = taskService.findTaskById(taskId);

        task.setName(updateTaskRequest.name());
        task.setDescription(updateTaskRequest.description());
        task.setTimer(updateTaskRequest.deadline());

        taskService.updateTask(task);
        return task;
    }

    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@RequestHeader(TOKEN) String token, @PathVariable("taskId") int taskId){
        taskService.deleteTaskById(taskId);
    }
}
