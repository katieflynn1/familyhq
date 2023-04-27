package com.fam.repository;

import com.fam.model.Goal;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByCreator(User user);

    List<Goal> findByAssignedUser(User user);
}