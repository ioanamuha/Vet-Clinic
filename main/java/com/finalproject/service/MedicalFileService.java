package com.finalproject.service;

import com.finalproject.entity.MedicalFile;

import java.util.List;

public interface MedicalFileService {
    void save(MedicalFile medicalFile);
    void deleteById(Long id);
    void update(MedicalFile medicalFile);
    MedicalFile findById(Long id);
    MedicalFile findByAppointmentId(Long appointmentId);
    List<MedicalFile> findByPetId(Long petId);
}
