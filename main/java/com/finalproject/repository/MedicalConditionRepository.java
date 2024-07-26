package com.finalproject.repository;

import com.finalproject.entity.MedicalCondition;
import com.finalproject.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
    List<MedicalCondition> findByPet(Pet pet);
}
