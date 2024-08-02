package com.finalproject.repository;

import com.finalproject.entity.DoctorDetails;
import com.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long> {
    DoctorDetails findByUserId(long id);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = :role")
    List<User> findUsersByRole(@Param("role") String role);

    @Query("SELECT DISTINCT d.specializare FROM DoctorDetails d")
    List<String> findAllSpecializations();

    @Query("SELECT d FROM DoctorDetails d WHERE d.specializare = :specialization")
    List<DoctorDetails> findDoctorsBySpecialization(@Param("specialization") String specialization);
}
