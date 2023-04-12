package com.fam.model;

import jakarta.persistence.*;
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList userTodoList;

    public Task() {}

    public Task(String description, boolean completed, TodoList userTodoList) {
        this.description = description;
        this.completed = completed;
        this.userTodoList = userTodoList;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TodoList getUserTodoList() {
        return userTodoList;
    }

    public void setUserTodoList(TodoList userTodoList) {
        this.userTodoList = userTodoList;
    }
}