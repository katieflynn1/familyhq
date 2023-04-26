package com.fam.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "instant_messages")
public class InstantMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_email")
    private String senderEmail;

   // @Column(name = "sender_last_name")
    //private String senderLastName;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_group_id")
    private FamilyGroup familyGroup;

    // Constructor, getters, and setters
    public InstantMessage() {}

    public InstantMessage(String senderEmail,String messageContent, LocalDateTime timestamp, FamilyGroup familyGroup) {
        this.senderEmail = senderEmail;
        //this.senderLastName = senderLastName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.familyGroup = familyGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

//    public String getSenderLastName() {
//        return senderLastName;
//    }
//
//    public void setSenderLastName(String senderLastName) {
//        this.senderLastName = senderLastName;
//    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public FamilyGroup getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(FamilyGroup familyGroup) {
        this.familyGroup = familyGroup;
    }
}