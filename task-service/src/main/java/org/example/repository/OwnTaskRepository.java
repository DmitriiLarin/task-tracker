package org.example.repository;

import org.example.entity.OwnTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnTaskRepository extends JpaRepository<OwnTask, Integer> {
    OwnTask findOwnTaskById(int id);
}
