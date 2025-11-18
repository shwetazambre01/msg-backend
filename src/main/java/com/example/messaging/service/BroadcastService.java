package com.example.messaging.service;

import com.example.messaging.model.Message;
import com.example.messaging.model.Recipient;
import com.example.messaging.repository.MessageRepository;
import com.example.messaging.repository.RecipientRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BroadcastService {

    private final RecipientRepository recipientRepository;
    private final MessageRepository messageRepository;

    public BroadcastService(RecipientRepository recipientRepository, MessageRepository messageRepository) {
        this.recipientRepository = recipientRepository;
        this.messageRepository = messageRepository;
    }

    public List<Recipient> listAllRecipients() {
        return recipientRepository.findAll();
    }

    public void broadcastText(String text) {
        List<Recipient> recipients = recipientRepository.findAll();
        for (Recipient r : recipients) {
            sendOutboundMessage(r, text);
        }
    }

    @Async
    public void sendOutboundMessage(Recipient recipient, String text) {
        Message m = new Message();
        m.setRecipient(recipient);
        m.setDirection(Message.Direction.OUTBOUND);
        m.setContent(text);
        m.setStatus("SENT");
        messageRepository.save(m);
    }
}
