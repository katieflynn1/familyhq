package com.fam.repository;

import com.fam.model.FamilyGroup;
import com.fam.model.FamilyRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRecipeRepository extends JpaRepository<FamilyRecipe, Long> {
    List<FamilyRecipe> findByFamilyGroup(FamilyGroup familyGroup);
}
