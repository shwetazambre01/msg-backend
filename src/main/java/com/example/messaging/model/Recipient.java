package com.example.messaging.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true)
    private String phone;
    private String email;
    private String importedFrom;
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getImportedFrom(){return importedFrom;}
    public void setImportedFrom(String importedFrom){this.importedFrom=importedFrom;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
