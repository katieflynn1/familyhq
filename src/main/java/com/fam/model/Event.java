package com.fam.model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "users_events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime start;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime end;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean completed;

    @Column
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User creator;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
//    private User assignedUser;

    @Column(nullable = false)
    private String assignedUserEmail;

    // getters and setters

    public Event(Long id, String title, LocalDateTime start, LocalDateTime end, String category, boolean completed, String notes, User creator, User assignedUser, String assignedUserEmail) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.category = category;
        this.completed = completed;
        this.notes = notes;
        this.creator = creator;
//        this.assignedUser = assignedUser;
        this.assignedUserEmail = assignedUserEmail;
    }

    public Event() {
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

//    public User getAssignedUser() {
//        return assignedUser;
//    }
//
//    public void setAssignedUser(User assignedUser) {
//        this.assignedUser = assignedUser;
//    }

    public String getAssignedUserEmail() {
        return assignedUserEmail;
    }

    public void setAssignedUserEmail(String assignedUserEmail) {
        this.assignedUserEmail = assignedUserEmail;
    }
}