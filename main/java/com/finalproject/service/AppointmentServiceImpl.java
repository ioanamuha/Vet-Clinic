package com.finalproject.service;

import com.finalproject.entity.Appointment;
import com.finalproject.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findByOwnerId(Long ownerId) {
        return appointmentRepository.findByMedicalCondition_Pet_Owner_Id(ownerId);
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        return appointmentRepository.findByMedicalCondition_Doctor_Id(doctorId);
    }
}
