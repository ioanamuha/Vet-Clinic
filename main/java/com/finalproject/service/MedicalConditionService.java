package com.finalproject.service;

import com.finalproject.entity.MedicalCondition;
import com.finalproject.entity.Pet;

import java.util.List;

public interface MedicalConditionService {

    List<MedicalCondition> findAll();
    MedicalCondition findById(Long id);
    void save(MedicalCondition medicalCondition);
    void deleteById(Long id);
    List<MedicalCondition> findByPet(Pet pet);
}
