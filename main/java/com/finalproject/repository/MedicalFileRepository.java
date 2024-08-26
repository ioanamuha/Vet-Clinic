package com.finalproject.repository;

import com.finalproject.entity.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalFileRepository extends JpaRepository<MedicalFile, Long> {
    List<MedicalFile> findByAppointment_Pet_Id(Long petId);
    MedicalFile findByAppointmentId(Long appointmentId);
}
