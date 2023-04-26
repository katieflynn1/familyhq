package com.fam.repository;

import com.fam.model.FamilyGroup;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {

    List<FamilyGroup> findAllByMembers(User user);

    Optional<FamilyGroup> findByMembersContaining(User user);

}