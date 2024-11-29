package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EventType;
import org.example.dto.TaskNotificationDTO;
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
    private final KafkaProducerService kafkaProducerService;

    public Task createTask(Task task) {
        kafkaProducerService.sendTask(new TaskNotificationDTO(task.getName(), task.getBoard().getName(),
                task.getBoard().getOwnerId(), EventType.TASK_ADD, task.getBoard().getId(),
                task.getId(), task.getTimer()));
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
        kafkaProducerService.sendTask(new TaskNotificationDTO(task.getName(), task.getBoard().getName(),
                task.getBoard().getOwnerId(), EventType.TASK_UPDATE, task.getBoard().getId(),
                task.getId(), task.getTimer()));
        return taskRepository.save(task);
    }

    public List<Task> findTasksByUser_id(UUID user_id) {
        return taskRepository.findTasksByUserId(user_id);
    }

    public void deleteTaskById(int id) {
        taskRepository.deleteById(id);
    }
}
