package com.finalproject.service;

import com.finalproject.entity.Appointment;
import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AppointmentServiceImpl appointmentService;
    private final MedicalFileServiceImpl medicalFileService;
    public PetServiceImpl(PetRepository petRepository, AppointmentServiceImpl appointmentService, MedicalFileServiceImpl medicalFileService) {
        this.petRepository = petRepository;
        this.appointmentService = appointmentService;
        this.medicalFileService = medicalFileService;
    }

    @Override
    public List<Pet> findByOwner(User user) {
        long id = user.getId();
        return petRepository.findByOwnerId(id);
    }

    @Override
    public List<Pet> findByOwnerEmail(String ownerEmail) {
        return petRepository.findByOwnerEmail(ownerEmail);
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet findByAppointmentId(Long appointmentId) {
        return petRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public Pet findById(long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Pet pet) {
        petRepository.save(pet);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        List<Appointment> appointments = appointmentService.findAllByPetId(id);
        if (appointments!=null) {
            for (Appointment appointment : appointments) {
                if (appointment.getMedicalFile() != null) {
                    medicalFileService.deleteById(appointment.getMedicalFile().getId());
                }
                appointmentService.deleteById(appointment.getId());
            }
        }
        petRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Pet pet) {
        petRepository.save(pet);
    }
}
