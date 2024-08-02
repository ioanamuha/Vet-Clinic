package com.finalproject.service;

import com.finalproject.entity.MedicalCondition;
import com.finalproject.repository.MedicalConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalConditionServiceImpl implements MedicalConditionService {

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    @Override
    public MedicalCondition findById(Long id) {
        return medicalConditionRepository.findById(id).orElse(null);
    }

    @Override
    public void save(MedicalCondition medicalCondition) {
        medicalConditionRepository.save(medicalCondition);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        medicalConditionRepository.deleteById(id);
    }

    @Override
    public List<MedicalCondition> findByPetId(Long petId) {
        return medicalConditionRepository.findByAppointment_Pet_Id(petId);
    }

    @Override
    public void update(MedicalCondition medicalCondition) {

        medicalConditionRepository.save(medicalCondition);
    }
}
