package com.finalproject.service;

import com.finalproject.entity.User;
import java.util.List;

public interface UserService {
    void save(User theUser);
    void save(User theUser, String theRole);
    void deleteById(long theId);
    void update(User theUser);
    User findById(long theId);
    User findByEmail(String theEmail);
    User findByPetId(Long petId);
    List<User> findAll();
    List<User> findAllPetOwners();
    List<User> findAllDoctors();
}
