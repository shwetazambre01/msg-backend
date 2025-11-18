package com.example.messaging.controller;

import com.example.messaging.model.Recipient;
import com.example.messaging.repository.RecipientRepository;
import com.example.messaging.service.BroadcastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broadcast")
public class BroadcastController {
    private final BroadcastService broadcastService;
    private final RecipientRepository recipientRepository;
    
    public BroadcastController(BroadcastService broadcastService, RecipientRepository recipientRepository) {
        this.broadcastService = broadcastService;
        this.recipientRepository = recipientRepository;
    }

    @GetMapping("/recipients")
    public ResponseEntity<List<Recipient>> listRecipients() {
        return ResponseEntity.ok(recipientRepository.findAll());
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendBroadcast(@RequestBody BroadcastRequest payload) {
        broadcastService.broadcastText(payload.getText());
        return ResponseEntity.ok().body("Broadcast triggered");
    }

    public static class BroadcastRequest {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}
