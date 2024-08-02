package com.finalproject.service;

import com.finalproject.entity.Pet;
import com.finalproject.entity.User;

import java.util.List;

public interface PetService {
    List<Pet> findByOwner(User user);
    void save(Pet pet);
    Pet findById(long id);
    void deleteById(long id);
    List<Pet> findAll();
    void update(Pet pet);
}
