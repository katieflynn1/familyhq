package com.fam.repository;

import com.fam.model.FamilyGroup;
import com.fam.model.InstantMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstantMessageRepository extends JpaRepository<InstantMessage, Long> {

    List<InstantMessage> findByFamilyGroupOrderByTimestampAsc(FamilyGroup familyGroup);
}