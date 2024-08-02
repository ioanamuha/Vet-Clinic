package com.finalproject.repository;

import com.finalproject.entity.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
    List<MedicalCondition> findByAppointment_Pet_Id(Long petId);

    @Modifying
    @Query("DELETE FROM MedicalCondition mc WHERE mc.appointment.id = :appointmentId")
    void deleteByAppointmentId(@Param("appointmentId") Long appointmentId);

    MedicalCondition findByAppointmentId(Long appointmentId);
}
