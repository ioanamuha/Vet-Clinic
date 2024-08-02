package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.Role;
import com.finalproject.entity.User;
import com.finalproject.repository.DoctorDetailsRepository;
import com.finalproject.repository.RoleRepository;
import com.finalproject.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long theId) {
        return userRepository.findById(theId).orElse(null);
    }

    @Override
    public User findByEmail(String theEmail) {
        return userRepository.findByEmail(theEmail);
    }

    @Override
    public void save(User theUser) {
        theUser.setPassword("{noop}" + theUser.getPassword());
        theUser.setEnabled(true);
        userRepository.save(theUser);

        Role userRole = new Role();
        userRole.setRole("ROLE_PETOWNER");
        userRole.setUser(theUser);

        roleRepository.save(userRole);
    }

    @Override
    public void deleteById(long theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public void update(User theUser) {
        theUser.setPassword("{noop}" + theUser.getPassword());
        theUser.setEnabled(true);
        userRepository.save(theUser);
    }

    @Override
    public DoctorDetails findDoctorDetailsByUserId(long theId) {
        return doctorDetailsRepository.findByUserId(theId);
    }


}
