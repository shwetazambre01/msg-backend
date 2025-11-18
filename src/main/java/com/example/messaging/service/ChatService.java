package com.example.messaging.service;

import com.example.messaging.model.Message;
import com.example.messaging.model.Recipient;
import com.example.messaging.repository.MessageRepository;
import com.example.messaging.repository.RecipientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final MessageRepository messageRepository;
    private final RecipientRepository recipientRepository;

    @PersistenceContext
    private EntityManager em;

    public ChatService(MessageRepository messageRepository, RecipientRepository recipientRepository) {
        this.messageRepository = messageRepository;
        this.recipientRepository = recipientRepository;
    }

    public List<Recipient> getRespondents() {
        String jpql = "SELECT DISTINCT m.recipient FROM Message m WHERE m.direction = :dir";
        Query q = em.createQuery(jpql);
        q.setParameter("dir", Message.Direction.INBOUND);
        return q.getResultList();
    }

    public List<Message> getConversation(Long recipientId) {
        Recipient r = recipientRepository.findById(recipientId).orElseThrow();
        return messageRepository.findByRecipientOrderBySentAtAsc(r);
    }

    public Message saveInboundReply(String phone, String text) {
        Recipient r = recipientRepository.findByPhone(phone).orElseThrow();
        Message m = new Message();
        m.setRecipient(r);
        m.setDirection(Message.Direction.INBOUND);
        m.setContent(text);
        m.setStatus("RECEIVED");
        return messageRepository.save(m);
    }
}
