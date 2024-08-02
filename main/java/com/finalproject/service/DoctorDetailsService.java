package com.finalproject.service;

import com.finalproject.entity.DoctorDetails;

import java.util.List;

public interface DoctorDetailsService {
    List<String> findAllSpecializations();
    List <DoctorDetails> findDoctorsBySpecialization(String specialization);
}
