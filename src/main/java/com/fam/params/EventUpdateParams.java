package com.fam.params;

import java.time.LocalDateTime;

public class EventUpdateParams {
    public String title;
    public LocalDateTime start;
    public LocalDateTime end;
    public String category;
    public Boolean completed;
    public long id;
    public String notes;
    public String user;
    public String assignedUserEmail; // modified field to use String instead of User

    // default constructor
    public EventUpdateParams() {
    }

    // parameterized constructor
    public EventUpdateParams(String title, LocalDateTime start, LocalDateTime end, String category, Boolean completed, String notes, String user, String assignedUserEmail) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.category = category;
        this.completed = completed;
        this.notes = notes;
        this.user = user;
        this.assignedUserEmail = assignedUserEmail;
    }

    // getters and setters

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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAssignedUserEmail() {
        return assignedUserEmail;
    }

    public void setAssignedUserEmail(String assignedUserEmail) {
        this.assignedUserEmail = assignedUserEmail;
    }
}