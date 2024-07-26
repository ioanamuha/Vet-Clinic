package com.finalproject.repository;

import com.finalproject.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p JOIN p.owner u WHERE u.email = :email")
    List<Pet> findByOwnerEmail(@Param("email") String email);

    List<Pet> findByOwnerId(long ownerId);

}
