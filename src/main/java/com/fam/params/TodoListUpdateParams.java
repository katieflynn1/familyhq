package com.fam.params;

public class TodoListUpdateParams {

    public String title;
    public String category;
    public Boolean completed;
    public long id;
    public long creatorId;
    public String assignedUserEmail;

    public TodoListUpdateParams(Long id, String title, Long creatorId, boolean completed, String assignedUserEmail) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.completed = completed;
        this.assignedUserEmail = assignedUserEmail;
    }

    public TodoListUpdateParams() {

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
