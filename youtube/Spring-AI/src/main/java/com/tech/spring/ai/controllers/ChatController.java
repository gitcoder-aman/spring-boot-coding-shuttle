package com.tech.spring.ai.controllers;

import com.tech.spring.ai.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ai")
@RestController
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String>chat(
            @RequestParam(value = "q",required = true) String q
    ){
        String resultResponse = chatService.chatTemplate(q);
        return ResponseEntity.ok(resultResponse);
    }
}
