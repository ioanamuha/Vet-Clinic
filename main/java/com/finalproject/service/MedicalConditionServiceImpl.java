package com.finalproject.service;

import com.finalproject.entity.MedicalCondition;
import com.finalproject.entity.Pet;
import com.finalproject.repository.MedicalConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionServiceImpl implements MedicalConditionService {

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    public List<MedicalCondition> findAll() {
        return medicalConditionRepository.findAll();
    }

    public MedicalCondition findById(Long id) {
        return medicalConditionRepository.findById(id).orElse(null);
    }

    public void save(MedicalCondition medicalCondition) {
        medicalConditionRepository.save(medicalCondition);
    }

    public void deleteById(Long id) {
        medicalConditionRepository.deleteById(id);
    }

    @Override
    public List<MedicalCondition> findByPet(Pet pet) {
        return medicalConditionRepository.findByPet(pet);
    }
}
