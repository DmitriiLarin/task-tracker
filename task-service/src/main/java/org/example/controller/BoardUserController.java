package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.UserClient;
import org.example.dto.BoardUserDTO;
import org.example.dto.UserDTO;
import org.example.dto.request.AddUserOnBoardRequest;
import org.example.dto.response.BoardInfoResponse;
import org.example.service.BoardUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/board-user")
@RequiredArgsConstructor
@Slf4j
public class BoardUserController {
    private final BoardUserService boardUserService;
    private final UserClient userClient;
    private final static String TOKEN = "Authorization";

    @PostMapping()
    public BoardUserDTO addUserToBoard(@RequestHeader(TOKEN) String token, @RequestBody AddUserOnBoardRequest addUserOnBoardRequest) {
        if (boardUserService.existsByBoardIdAndUserId(addUserOnBoardRequest.boardId(),
                addUserOnBoardRequest.userId())) {
            return null;
        }
        BoardUserDTO boardUserDTO = new BoardUserDTO(addUserOnBoardRequest.boardId(), addUserOnBoardRequest.userId());
        boardUserService.save(boardUserDTO);
        return boardUserDTO;
    }

    @GetMapping()
    public List<BoardInfoResponse> getBoards(@RequestHeader(TOKEN) String token){
        UUID user_id = userClient.getUserByToken(token);
        return boardUserService.getBoards(user_id);
    }

    @GetMapping("/{boardId}/users")
    public List<UserDTO> getUsers(@RequestHeader(TOKEN) String token, @PathVariable("boardId") int boardId){
        List<UUID> users_id = boardUserService.findUserIdsByBoardId(boardId);
        List<UserDTO> list = new ArrayList<>();
        for (UUID uuid : users_id) {
            list.add(userClient.getUserData(token, uuid));
        }
        return list;
    }
}
