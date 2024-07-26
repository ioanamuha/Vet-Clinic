package com.finalproject.repository;

import com.finalproject.entity.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long> {
    DoctorDetails findByUserId(long id);
}
