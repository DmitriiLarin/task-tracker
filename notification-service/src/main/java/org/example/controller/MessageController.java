package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.UserClient;
import org.example.dto.response.MessageResponse;
import org.example.service.MessageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MessageController {
    private final UserClient userClient;
    private final MessageService messageService;
    private final static String TOKEN = "Authorization";

    @GetMapping()
    public List<MessageResponse> getUserTasks(@RequestHeader(TOKEN) String token){
        log.info("getUserTasks");
        UUID userId = userClient.getUserByToken(token);
        log.info("userId: {}", userId);
        return messageService.getMessagesByUserId(userId).stream().map(message ->
                new MessageResponse(message.getText(), message.getCreatedAt())).toList();
    }
}