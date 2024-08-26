package com.finalproject.repository;

import com.finalproject.entity.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long> {

    DoctorDetails findByUserId(long id);

    @Query("SELECT DISTINCT d.specializare " +
            "FROM DoctorDetails d " +
            "JOIN d.user u " +
            "JOIN u.role r " +
            "WHERE d.specializare IS NOT NULL " +
            "AND r.role = 'ROLE_DOCTOR'")
    List<String> findAllSpecializations();

    @Query("SELECT d FROM DoctorDetails d WHERE d.specializare = :specialization")
    List<DoctorDetails> findDoctorsBySpecialization(@Param("specialization") String specialization);

    DoctorDetails findByUserEmail(String currentUserEmail);
}
