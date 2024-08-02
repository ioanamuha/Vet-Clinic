package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.repository.DoctorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorDetailsServiceImpl implements DoctorDetailsService{

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Override
    public List<String> findAllSpecializations() {
        return doctorDetailsRepository.findAllSpecializations();
    }

    @Override
    public List<DoctorDetails> findDoctorsBySpecialization(String specialization) {
        return doctorDetailsRepository.findDoctorsBySpecialization(specialization);
    }

}
