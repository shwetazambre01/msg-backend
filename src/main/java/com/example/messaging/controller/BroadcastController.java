package com.example.messaging.controller;

import com.example.messaging.service.BroadcastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/broadcast")
public class BroadcastController {
    private final BroadcastService broadcastService;
    public BroadcastController(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
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
