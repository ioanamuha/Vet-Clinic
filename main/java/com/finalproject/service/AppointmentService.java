package com.finalproject.service;

import com.finalproject.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    void save(Appointment appointment);

    void deleteById(Long id);

    void book(Long petId, Long doctorId, LocalDate date, Integer interval, String mail);

    void update(Appointment appointment);

    Appointment findById(Long appointmentId);

    List<Appointment> getAvailableSlots(Long doctorId, LocalDate date);

    List<Appointment> findAllByPetId(Long petId);

    List<Appointment> findAllByOwnerEmail(String ownerEmail);

    List<Appointment> findAllByDoctorEmail(String doctorEmail);
}
