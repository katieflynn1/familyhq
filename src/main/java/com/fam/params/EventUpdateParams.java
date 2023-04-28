package com.fam.params;

import java.time.LocalDateTime;
import java.util.List;

public class EventUpdateParams {
    public String title;
    public LocalDateTime start;
    public LocalDateTime end;
    public String category;
    public Boolean completed;
    public long id;
    public String notes;
    public String user;
    public List<String> assignedUserEmails;

    // default constructor
    public EventUpdateParams() {
    }

    // parameterized constructor
    public EventUpdateParams(String title, LocalDateTime start, LocalDateTime end, String category, Boolean completed, String notes, String user) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.category = category;
        this.completed = completed;
        this.notes = notes;
        this.user = user;
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

    public List<String> getAssignedUserEmails() {
        return assignedUserEmails;
    }

    public void setAssignedUserEmails(List<String> assignedUserEmails) {
        this.assignedUserEmails = assignedUserEmails;
    }
}