package com.finalproject.repository;

import com.finalproject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.day = :day AND a.interval = :interval")
    boolean slotExists(@Param("doctorId") Long doctorId, @Param("day") LocalDate day, @Param("interval") int interval);

    List<Appointment> findByPetIdOrderByDayAscIntervalAsc(Long petId);

    @Query("SELECT a FROM Appointment a WHERE a.pet.owner.email = :ownerEmail ORDER BY a.day ASC, a.interval ASC")
    List<Appointment> findAllByOwnerEmail(@Param("ownerEmail") String ownerEmail);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.email = :doctorEmail ORDER BY a.day ASC, a.interval ASC")
    List<Appointment> findAllByDoctorEmail(@Param("doctorEmail") String doctorEmail);
}
