package com.example.messaging.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="recipient_id")
    private Recipient recipient;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String status;
    private LocalDateTime sentAt = LocalDateTime.now();

    public static enum Direction { OUTBOUND, INBOUND }

    // getters & setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public Recipient getRecipient(){return recipient;}
    public void setRecipient(Recipient recipient){this.recipient=recipient;}
    public Direction getDirection(){return direction;}
    public void setDirection(Direction direction){this.direction=direction;}
    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public LocalDateTime getSentAt(){return sentAt;}
    public void setSentAt(LocalDateTime sentAt){this.sentAt=sentAt;}
}
