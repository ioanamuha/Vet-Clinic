package com.finalproject.service;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.MedicalCondition;
import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.repository.AppointmentRepository;
import com.finalproject.repository.MedicalConditionRepository;
import com.finalproject.repository.PetRepository;
import com.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAvailableSlots(Long doctorId, LocalDate date) {
        List<Appointment> availableSlots = new ArrayList<>();

        for (int interval = 1; interval <= 4; interval++) {
            if (!appointmentRepository.slotExists(doctorId, date, interval)) {
                Appointment newSlot = new Appointment();
                User doctor = new User();
                doctor.setId(doctorId);
                newSlot.setDoctor(doctor);
                newSlot.setDay(date);
                newSlot.setInterval(interval);
                availableSlots.add(newSlot);
            }
        }

        return availableSlots;
    }

    @Override
    public void book(Long petId, Long doctorId, LocalDate date, Integer interval, String mail) {
        Appointment appointment = new Appointment();

        appointment.setDoctor(userRepository.findById(doctorId).orElse(null));
        appointment.setPet(petRepository.findById(petId).orElse(null));

        appointment.setDay(date);
        appointment.setInterval(interval);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByPetId(Long petId) {
        return appointmentRepository.findByPetIdOrderByDayAscIntervalAsc(petId);
    }

    @Override
    public List<Appointment> findAllByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorIdOrderByDayAscIntervalAsc(doctorId);
    }

    @Override
    public Appointment findById(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        return appointment;
    }

    @Override
    public void update(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByOwnerId(Long userId) {
        return appointmentRepository.findByOwnerId(userId);
    }


}
