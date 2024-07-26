package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long theId);
    User findByEmail(String theEmail);
    void save(User theUser);
    void deleteById(long theId);
    void update(User theUser);
    DoctorDetails findDoctorDetailsByUserId(long theId);
}
