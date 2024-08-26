package com.finalproject.repository;

import com.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.pets p WHERE p.id = :petId")
    User findByPetId(@Param("petId") Long petId);

}
