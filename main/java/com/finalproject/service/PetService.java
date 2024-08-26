package com.finalproject.service;

import com.finalproject.entity.Pet;
import com.finalproject.entity.User;

import java.util.List;

public interface PetService {
    List<Pet> findByOwner(User user);
    List<Pet> findByOwnerEmail(String ownerEmail);
    List<Pet> findAll();
    Pet findByAppointmentId(Long appointmentId);
    Pet findById(long id);
    void save(Pet pet);
    void deleteById(long id);
    void update(Pet pet);
}
