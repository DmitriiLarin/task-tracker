package org.example.repository;

import feign.Param;
import org.example.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT DISTINCT b FROM Board b LEFT JOIN b.tasks t WHERE b.ownerId = :userId OR t.userId = :userId")
    List<Board> findUniqueBoardsByOwnerOrTaskUserId(@Param("userId") UUID userId);
}
