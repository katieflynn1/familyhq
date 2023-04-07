package com.fam.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user_event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(name = "start_time", nullable = false)
    public LocalDateTime start;

    @Column(name = "end_time", nullable = false)
    public LocalDateTime end;

    @Column(nullable = false)
    public String category;

    @Column(nullable = false)
    private boolean completed;

    @Column
    public String notes;

    @Column
    public String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    public Event() {
    }

    public Event(String title, LocalDateTime start, LocalDateTime end, String category, boolean completed, String notes, String color, User user) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.category = category;
        this.completed = completed;
        this.notes = notes;
        this.color = color;
        this.user = user;
    }

    // Getters and setters
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

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}