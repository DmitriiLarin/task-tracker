package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BoardUserDTO;
import org.example.dto.EventType;
import org.example.dto.MessageDTO;
import org.example.dto.response.BoardInfoResponse;
import org.example.entity.BoardUser;
import org.example.repository.BoardUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardUserService {
    private final BoardUserRepository boardUserRepository;
    private final BoardService boardService;
    private final KafkaProducerService kafkaProducerService;

    public void save(BoardUserDTO boardUserDTO) {
        BoardUser boardUser = new BoardUser();
        boardUser.setBoardId(boardUserDTO.boardId());
        boardUser.setUserId(boardUserDTO.userId());

        kafkaProducerService.sendMessage(new MessageDTO(EventType.BOARD_ADD,
                boardUserDTO.userId(), boardService.getNameById(boardUser.getBoardId())));
        boardUserRepository.save(boardUser);
    }

    public List<BoardInfoResponse> getBoards(UUID userId) {
        List<BoardUser> boardUsers = boardUserRepository.findByUserId(userId);
        List<BoardInfoResponse> boardResponses = new ArrayList<>();
        for (BoardUser boardUser : boardUsers) {
            boardResponses.add(new BoardInfoResponse(boardUser.getBoardId(),
                    boardService.getNameById(boardUser.getBoardId())));
        }
        return boardResponses;
    }

    public List<UUID> findUserIdsByBoardId(int boardId){
        return boardUserRepository.findUserIdsByBoardId(boardId);
    }

    public boolean existsByBoardIdAndUserId(int boardId, UUID userId) {
        return boardUserRepository.existsByBoardIdAndUserId(boardId, userId);
    }

}
