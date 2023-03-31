package com.fam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fam.model.Event;
import com.fam.model.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUser(User user);
}