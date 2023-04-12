package com.fam.repository;

import com.fam.model.Event;
import com.fam.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Long> {
    List<TodoList> findByCreatorId(Long creatorId);
    List<TodoList> findByAssignedUserEmail(String assignedUserEmail);
    @Query("select e from TodoList e where e.assignedUserEmail = :email or e.creatorId = :id")
    List<TodoList> findByCreatorIdOrAssignedUserEmail(@Param("id") Long creatorId, @Param("email") String email);
    TodoList getOne(Long id);
    Optional<TodoList> findById(Long id);
}
