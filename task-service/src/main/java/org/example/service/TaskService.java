package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UpdateTaskPriorityRequest;
import org.example.entity.Task;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updatePriority(UpdateTaskPriorityRequest updateTaskPriorityRequest) {
        Task task = taskRepository.findById(updateTaskPriorityRequest.taskId()).orElseThrow();
        task.setPriority(updateTaskPriorityRequest.priority());
        return taskRepository.save(task);
    }

    public Task findTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findTasksByUser_id(UUID user_id) {
        return taskRepository.findTasksByUserId(user_id);
    }

    public void deleteTaskById(int id) {
        taskRepository.deleteById(id);
    }
}
