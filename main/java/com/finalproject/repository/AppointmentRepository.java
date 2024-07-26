package com.finalproject.repository;

import com.finalproject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByMedicalCondition_Pet_Owner_Id(Long ownerId);
    List<Appointment> findByMedicalCondition_Doctor_Id(Long doctorId);
}
