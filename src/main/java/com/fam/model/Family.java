package com.fam.model;

import jakarta.persistence.*;
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FamilyStatus status;

    private String familyRole;

    public Family() {
    }

    public Family(User sender, User receiver, FamilyStatus status, String familyRole) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.familyRole = familyRole;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public FamilyStatus getStatus() {
        return status;
    }

    public void setStatus(FamilyStatus status) {
        this.status = status;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }
}