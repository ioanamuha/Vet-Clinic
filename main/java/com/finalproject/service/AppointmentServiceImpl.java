package com.finalproject.service;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.User;
import com.finalproject.repository.AppointmentRepository;
import com.finalproject.repository.PetRepository;
import com.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PetRepository petRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void book(Long petId, Long doctorId, LocalDate date, Integer interval, String mail) {
        Appointment appointment = new Appointment();

        appointment.setDoctor(userRepository.findById(doctorId).orElse(null));
        appointment.setPet(petRepository.findById(petId).orElse(null));

        appointment.setDay(date);
        appointment.setInterval(interval);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void update(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
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
    public List<Appointment> findAllByPetId(Long petId) {
        return appointmentRepository.findByPetIdOrderByDayAscIntervalAsc(petId);
    }

    @Override
    public List<Appointment> findAllByOwnerEmail(String ownerEmail) {
        return appointmentRepository.findAllByOwnerEmail(ownerEmail);
    }

    @Override
    public List<Appointment> findAllByDoctorEmail(String doctorEmail) {
        return appointmentRepository.findAllByDoctorEmail(doctorEmail);
    }


}
