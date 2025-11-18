package com.example.messaging.controller;

import com.example.messaging.model.Message;
import com.example.messaging.model.Recipient;
import com.example.messaging.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) { this.chatService = chatService; }

    @GetMapping("/respondents")
    public ResponseEntity<List<Recipient>> respondents() {
        return ResponseEntity.ok(chatService.getRespondents());
    }

    @GetMapping("/conversation/{recipientId}")
    public ResponseEntity<List<Message>> conversation(@PathVariable Long recipientId) {
        return ResponseEntity.ok(chatService.getConversation(recipientId));
    }

    @PostMapping("/receive")
    public ResponseEntity<Message> receiveReply(@RequestBody ReceiveDto dto) {
        Message saved = chatService.saveInboundReply(dto.getPhone(), dto.getText());
        return ResponseEntity.ok(saved);
    }

    public static class ReceiveDto {
        private String phone;
        private String text;
        public String getPhone(){return phone;}
        public void setPhone(String phone){this.phone=phone;}
        public String getText(){return text;}
        public void setText(String text){this.text=text;}
    }
}
