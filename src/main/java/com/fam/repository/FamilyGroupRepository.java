package com.fam.repository;

import com.fam.model.FamilyGroup;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {
    Optional<FamilyGroup> findByGroupName(String groupName);

    Optional<FamilyGroup> findByUserId(Long id);

    List<FamilyGroup> findAllByMembers(User user);
}