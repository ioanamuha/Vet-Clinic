package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.Pet;
import com.finalproject.entity.Role;
import com.finalproject.entity.User;
import com.finalproject.repository.DoctorDetailsRepository;
import com.finalproject.repository.RoleRepository;
import com.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;
    private final PetService petService;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, DoctorDetailsRepository doctorDetailsRepository, PetService petService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
        this.petService = petService;
    }

    @Override
    public void save(User theUser) {

        String password=theUser.getPassword();
        password=passwordEncoder.encode(password);

        theUser.setPassword(password);
        theUser.setEnabled(true);
        theUser = userRepository.save(theUser);

        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setUser(theUser);
        doctorDetails.setSpecializare("");
        doctorDetailsRepository.save(doctorDetails);

        Role userRole = new Role();
        userRole.setRole("ROLE_PETOWNER");
        userRole.setUser(theUser);

        roleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void save(User theUser, String theRole) {

        String password=theUser.getPassword();
        password=passwordEncoder.encode(password);

        theUser.setPassword(password);
        theUser.setEnabled(true);
        theUser = userRepository.save(theUser);

        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setUser(theUser);
        doctorDetails.setSpecializare("");
        doctorDetailsRepository.save(doctorDetails);

        Role userRole = new Role();
        userRole.setRole(theRole);
        userRole.setUser(theUser);

        roleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void deleteById(long theId) {
        User user = userRepository.findById(theId).orElse(null);
        if(user != null ) {
            List<Pet> pets = user.getPets();
            for (Pet pet : pets) {
                if (pet != null) {
                    petService.deleteById(pet.getId());
                }
            }
            userRepository.deleteById(theId);
        }
    }

    @Override
    @Transactional
    public void update(User theUser) {

        String password=theUser.getPassword();
        password=passwordEncoder.encode(password);
        theUser.setPassword(password);
        theUser.setEnabled(true);
        userRepository.save(theUser);
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
    public User findByPetId(Long petId) {
        return userRepository.findByPetId(petId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllPetOwners() {

        List<User> petOwners = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if(user.getRole().getRole().equals("ROLE_PETOWNER")) {
                petOwners.add(user);
            }
        }
        return petOwners;
    }

    @Override
    public List<User> findAllDoctors() {
        List<User> doctors = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if(user.getRole().getRole().equals("ROLE_DOCTOR")) {
                doctors.add(user);
            }
        }
        return doctors;
    }
}
