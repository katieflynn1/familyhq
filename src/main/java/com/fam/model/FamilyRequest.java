package com.fam.model;

import jakarta.persistence.*;
@Entity
@Table(name = "family_requests")
public class FamilyRequest {
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
    private FamilyRequestStatus status;

    private String familyRole;

    private String email;

    public FamilyRequest() {
    }

    public FamilyRequest(User sender, User receiver, FamilyRequestStatus status, String familyRole) {
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

    public FamilyRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FamilyRequestStatus status) {
        this.status = status;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }

    public String getEmail() { return email;}
    }
