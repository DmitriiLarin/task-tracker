package org.example.repository;

import org.example.entity.OwnTask;
import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findTasksByUserId(UUID user_id);

    Task findTasksById(int id);
}
