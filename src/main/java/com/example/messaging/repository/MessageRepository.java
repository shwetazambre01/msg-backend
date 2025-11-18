package com.example.messaging.repository;

import com.example.messaging.model.Message;
import com.example.messaging.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientOrderBySentAtAsc(Recipient recipient);
}
