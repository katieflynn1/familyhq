package com.fam.repository;

import com.fam.model.FamilyGroup;
import com.fam.model.Role;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);

    @Query("SELECT u FROM User u JOIN u.familyGroups fg WHERE u.role = :role AND fg = :familyGroup")
    List<User> findByRoleAndFamilyGroups(@Param("role") Role role, @Param("familyGroup") FamilyGroup familyGroup);
}