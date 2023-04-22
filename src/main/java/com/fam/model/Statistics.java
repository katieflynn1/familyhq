package com.fam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "num_events")
    private Long numEvents;

    @Column(name = "num_completed_events")
    private Long numCompletedEvents;

    @Column(name = "num_incomplete_events")
    private Long numIncompleteEvents;

    @Column(name = "event_completion_ratio")
    private Double eventCompletionRatio;

    @Column(name = "num_todo_lists")
    private Long numTodoLists;

    @Column(name = "num_completed_todo_lists")
    private Long numCompletedTodoLists;

    @Column(name = "num_incomplete_todo_lists")
    private Long numIncompleteTodoLists;

    @Column(name = "todo_list_completion_ratio")
    private Double todoListCompletionRatio;

    @Column(name = "num_tasks")
    private Long numTasks;

    @Column(name = "num_completed_tasks")
    private Long numCompletedTasks;

    @Column(name = "num_incomplete_tasks")
    private Long numIncompleteTasks;

    @Column(name = "task_completion_ratio")
    private Double taskCompletionRatio;

    public Statistics() {
    }

    public Statistics(long numEvents, long numCompletedEvents, long numIncompleteEvents, Double eventCompletionRatio,
                      long numTodoLists, long numCompletedTodoLists, long numIncompleteTodoLists, Double todoListCompletionRatio,
                      long numTasks, long numCompletedTasks, long numIncompleteTasks, Double taskCompletionRatio) {
        this.numEvents = numEvents;
        this.numCompletedEvents = numCompletedEvents;
        this.numIncompleteEvents = numIncompleteEvents;
        this.eventCompletionRatio = eventCompletionRatio;
        this.numTodoLists = numTodoLists;
        this.numCompletedTodoLists = numCompletedTodoLists;
        this.numIncompleteTodoLists = numIncompleteTodoLists;
        this.todoListCompletionRatio = todoListCompletionRatio;
        this.numTasks = numTasks;
        this.numCompletedTasks = numCompletedTasks;
        this.numIncompleteTasks = numIncompleteTasks;
        this.taskCompletionRatio = taskCompletionRatio;
    }
    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(Long numEvents) {
        this.numEvents = numEvents;
    }

    public Long getNumCompletedEvents() {
        return numCompletedEvents;
    }

    public void setNumCompletedEvents(Long numCompletedEvents) {
        this.numCompletedEvents = numCompletedEvents;
    }

    public Long getNumIncompleteEvents() {
        return numIncompleteEvents;
    }

    public void setNumIncompleteEvents(Long numIncompleteEvents) {
        this.numIncompleteEvents = numIncompleteEvents;
    }

    public Double getEventCompletionRatio() {
        return eventCompletionRatio;
    }

    public void setEventCompletionRatio(Double eventCompletionRatio) {
        this.eventCompletionRatio = eventCompletionRatio;
    }

    public Long getNumTodoLists() {
        return numTodoLists;
    }

    public void setNumTodoLists(Long numTodoLists) {
        this.numTodoLists = numTodoLists;
    }

    public Long getNumCompletedTodoLists() {
        return numCompletedTodoLists;
    }

    public void setNumCompletedTodoLists(Long numCompletedTodoLists) {
        this.numCompletedTodoLists = numCompletedTodoLists;
    }

    public Long getNumIncompleteTodoLists() {
        return numIncompleteTodoLists;
    }

    public void setNumIncompleteTodoLists(Long numIncompleteTodoLists) {
        this.numIncompleteTodoLists = numIncompleteTodoLists;
    }

    public Double getTodoListCompletionRatio() {
        return todoListCompletionRatio;
    }

    public void setTodoListCompletionRatio(Double todoListCompletionRatio) {
        this.todoListCompletionRatio = todoListCompletionRatio;
    }

    public Long getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(Long numTasks) {
        this.numTasks = numTasks;
    }

    public Long getNumCompletedTasks() {
        return numCompletedTasks;
    }

    public void setNumCompletedTasks(Long numCompletedTasks) {
        this.numCompletedTasks = numCompletedTasks;
    }

    public Long getNumIncompleteTasks() {
        return numIncompleteTasks;
    }

    public void setNumIncompleteTasks(Long numIncompleteTasks) {
        this.numIncompleteTasks = numIncompleteTasks;
    }

    public Double getTaskCompletionRatio() {
        return taskCompletionRatio;
    }

    public void setTaskCompletionRatio(Double taskCompletionRatio) {
        this.taskCompletionRatio = taskCompletionRatio;
    }
}