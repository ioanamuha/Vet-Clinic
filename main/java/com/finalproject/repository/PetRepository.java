package com.finalproject.repository;

import com.finalproject.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwnerId(long ownerId);

    @Query("SELECT p FROM Pet p JOIN p.appointments a WHERE a.id = :appointmentId")
    Pet findByAppointmentId(@Param("appointmentId") Long appointmentId);

    List<Pet> findByOwnerEmail(String ownerEmail);
}
