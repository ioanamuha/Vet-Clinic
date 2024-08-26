package com.finalproject.service;

import com.finalproject.entity.MedicalFile;
import com.finalproject.repository.MedicalFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalFileServiceImpl implements MedicalFileService {

    private final MedicalFileRepository medicalFileRepository;
    public MedicalFileServiceImpl(MedicalFileRepository medicalFileRepository) {
        this.medicalFileRepository = medicalFileRepository;
    }

    @Override
    @Transactional
    public void save(MedicalFile medicalFile) {
        medicalFileRepository.save(medicalFile);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        medicalFileRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(MedicalFile medicalFile) {
        medicalFileRepository.save(medicalFile);
    }

    @Override
    public MedicalFile findById(Long id) {
        return medicalFileRepository.findById(id).orElse(null);
    }

    @Override
    public MedicalFile findByAppointmentId(Long appointmentId) {
        return medicalFileRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public List<MedicalFile> findByPetId(Long petId) {
        return medicalFileRepository.findByAppointment_Pet_Id(petId);
    }
}
