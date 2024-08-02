package com.finalproject.service;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.MedicalCondition;

import java.util.List;

public interface MedicalConditionService {
    MedicalCondition findById(Long id);
    void save(MedicalCondition medicalCondition);
    void deleteById(Long id);
    List<MedicalCondition> findByPetId(Long petId);
    void update(MedicalCondition medicalCondition);
}
