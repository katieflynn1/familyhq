package com.fam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fam.model.Event;
import com.fam.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("from Event e where not(e.end < :from or e.start > :to)")
    public List<Event> findBetween(@Param("from") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Query("SELECT e FROM Event e JOIN e.assignedUsers eu WHERE e.creator.email = ?1 OR eu.user.email = ?1")
    List<Event> findByEmailOrUserEmail(String email);

    @Query("SELECT e FROM Event e JOIN e.assignedUsers eu WHERE e.creator.email = :email OR eu.user.email = :email")
    List<Event> findByCreatorEmailOrAssignedUserEmail(@Param("email") String email);

    long countByCreator(User currentUser);

    long countByCreatorAndCompleted(User currentUser, boolean completed);

    long countByCompleted(boolean completed);

    List<Event> findByCreator(User currentUser);
}
