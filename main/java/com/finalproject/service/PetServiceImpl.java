package com.finalproject.service;

import com.finalproject.entity.Pet;
import com.finalproject.entity.User;
import com.finalproject.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> findByOwnerEmail(String email) {
        return petRepository.findByOwnerEmail(email);
    }

    @Override
    public List<Pet> findByOwnerId(long id) {
        return petRepository.findByOwnerId(id);
    }

    @Override
    public List<Pet> findByOwner(User user) {
        long id = user.getId();
        return petRepository.findByOwnerId(id);
    }

    @Override
    public void save(Pet pet) {
        petRepository.save(pet);
    }

    @Override
    public Pet findById(long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public void update(Pet pet) {
        petRepository.save(pet);
    }
}
