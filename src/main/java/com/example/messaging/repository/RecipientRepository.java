package com.example.messaging.repository;

import com.example.messaging.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Optional<Recipient> findByPhone(String phone);
}
