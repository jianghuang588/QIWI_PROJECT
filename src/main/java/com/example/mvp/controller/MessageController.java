package com.example.mvp.controller;

import com.example.mvp.database_table.Message;
import com.example.mvp.dto.MessageDto;
import com.example.mvp.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// MessageController
@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDto dto) {
        try {
            Message message = messageService.sendMessage(dto);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable Long user1Id, @PathVariable Long user2Id) {
        return ResponseEntity.ok(messageService.getConversation(user1Id, user2Id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getUserMessages(userId));
    }
}
