package com.fam.repository;

import com.fam.model.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventUserRepository extends JpaRepository<EventUser, Long> {

}