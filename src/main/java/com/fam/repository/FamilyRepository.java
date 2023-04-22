package com.fam.repository;

import com.fam.model.Family;
import com.fam.model.FamilyStatus;
import com.fam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findBySenderAndReceiver(User sender, User receiver);

    List<Family> findByReceiverAndStatus(User currentUser, FamilyStatus familyStatus);
}