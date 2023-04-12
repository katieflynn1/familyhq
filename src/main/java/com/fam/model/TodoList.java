package com.fam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_todolists")
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "assigned_user_email")
    private String assignedUserEmail;

    // Constructors, getters, setters,
    public TodoList(Long id, String title, Long creatorId, boolean completed, String assignedUserEmail) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.completed = completed;
        this.assignedUserEmail = assignedUserEmail;
    }

    public TodoList() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getAssignedUserEmail() {
        return assignedUserEmail;
    }

    public void setAssignedUserEmail(String assignedUserEmail) {
        this.assignedUserEmail = assignedUserEmail;
    }
}

