package com.fam.repository;

import com.fam.model.Task;
import com.fam.model.TodoList;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTodoList(TodoList todoList);

    long countByTodoList_CreatorId(Long creatorId);

    long countByTodoList_CreatorIdAndCompleted(Long creatorId, boolean completed);
}