package com.fam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fam.model.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

}
