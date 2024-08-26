package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.User;
import com.finalproject.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorDetailsServiceImpl implements DoctorDetailsService{

    private final DoctorDetailsRepository doctorDetailsRepository;
    private final UserService userService;
    public DoctorDetailsServiceImpl(DoctorDetailsRepository doctorDetailsRepository, UserService userService) {
        this.doctorDetailsRepository = doctorDetailsRepository;
        this.userService = userService;
    }

    @Override
    public List<String> findAllSpecializations() {
        return doctorDetailsRepository.findAllSpecializations();
    }

    @Override
    public List<DoctorDetails> findDoctorsBySpecialization(String specialization) {
        return doctorDetailsRepository.findDoctorsBySpecialization(specialization);
    }

    @Override
    public DoctorDetails findByUserEmail(String currentUserEmail) {
        return doctorDetailsRepository.findByUserEmail(currentUserEmail);
    }

    @Override
    public void save(DoctorDetails doctorDetails) {
        doctorDetailsRepository.save(doctorDetails);
    }

    @Override
    public void updateDoctorSpecialization(long userId, String newSpecialization) {

        DoctorDetails doctorDetails = doctorDetailsRepository.findByUserId(userId);
        doctorDetails.setSpecializare(newSpecialization);
        save(doctorDetails);

        User user = userService.findById(userId);
        user.setDoctorDetails(doctorDetails);
    }

    @Override
    public String findSpecializationByUserEmail(String doctorEmail) {
        return doctorDetailsRepository.findByUserEmail(doctorEmail).getSpecializare();
    }

}
