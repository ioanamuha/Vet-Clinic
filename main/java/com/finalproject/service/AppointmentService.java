package com.finalproject.service;

import com.finalproject.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();
    Appointment findById(Long id);
    void save(Appointment appointment);
    void deleteById(Long id);
    List<Appointment> findByOwnerId(Long ownerId);
    List<Appointment> findByDoctorId(Long doctorId);
}
