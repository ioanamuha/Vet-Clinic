package com.finalproject.service;

import com.finalproject.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    void save(Appointment appointment);

    void deleteById(Long id);

    List<Appointment> getAvailableSlots(Long doctorId, LocalDate date);

    void book(Long petId, Long doctorId, LocalDate date, Integer interval, String mail);

    List<Appointment> findAllByPetId(Long petId);

    List<Appointment> findAllByDoctorId(Long doctorId);

    Appointment findById(Long appointmentId);

    void update(Appointment appointment);

    List<Appointment> findAllByOwnerId(Long userId);
}
