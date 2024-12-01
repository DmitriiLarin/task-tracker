package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.EventType;
import org.example.dto.TaskNotificationDTO;
import org.example.dto.request.UpdateTaskPriorityRequest;
import org.example.entity.OwnTask;
import org.example.entity.TaskType;
import org.example.repository.OwnTaskRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnTaskService {

    private final OwnTaskRepository ownTaskRepository;
    private final KafkaProducerService kafkaProducerService;

    public OwnTask updatePriority(UpdateTaskPriorityRequest updateTaskPriorityRequest) {
        OwnTask ownTask = ownTaskRepository.findById(updateTaskPriorityRequest.taskId()).orElseThrow();
        ownTask.setPriority(updateTaskPriorityRequest.priority());
        return ownTaskRepository.save(ownTask);
    }

    public OwnTask findTaskById(int id) {
        return ownTaskRepository.findById(id).orElse(null);
    }

    public void updateTask(OwnTask ownTask) {
        kafkaProducerService.sendTask(new TaskNotificationDTO(ownTask.getName(), "Моя таблица задач",
                ownTask.getOwnBoard().getOwnerId(), EventType.TASK_UPDATE, TaskType.OWN_TASK,
                ownTask.getId(), ownTask.getTimer()));
        ownTaskRepository.save(ownTask);
    }

    public void saveTask(OwnTask ownTask) {
        ownTaskRepository.save(ownTask);
        kafkaProducerService.sendTask(new TaskNotificationDTO(ownTask.getName(), "Моя таблица задач",
                ownTask.getOwnBoard().getOwnerId(), EventType.TASK_ADD, TaskType.OWN_TASK,
                ownTask.getId(), ownTask.getTimer()));
    }

    public void deleteTaskById(int id) {

        OwnTask ownTask =  ownTaskRepository.findOwnTaskById(id);

        kafkaProducerService.sendTask(new TaskNotificationDTO(ownTask.getName(), "Моя таблица задач",
                ownTask.getOwnBoard().getOwnerId(), EventType.TASK_DELETE, TaskType.OWN_TASK,
                ownTask.getId(), ownTask.getTimer()));

        ownTaskRepository.deleteById(id);
    }
}
