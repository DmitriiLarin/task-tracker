package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BoardDTO;
import org.example.entity.Board;
import org.example.entity.Task;
import org.example.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Task> findTasksByBoardId(int boardId){
        return boardRepository.findById(boardId).get().getTasks();
    }

    public void deleteBoard(int boardId){
        boardRepository.deleteById(boardId);
    }

    public Board addBoard(BoardDTO boardDTO) {
        Board board = new Board();
        board.setName(boardDTO.name());
        board.setOwnerId(boardDTO.ownerId());
        return boardRepository.save(board);
    }

    public Board getByBoardId(int boardId) {
        return boardRepository.findById(boardId).get();
    }

    public String getNameById(int boardId){
        return boardRepository.findById(boardId).get().getName();
    }
}