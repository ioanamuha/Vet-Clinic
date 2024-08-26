package com.finalproject.repository;

import com.finalproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r.role FROM Role r WHERE r.user.id = :userId")
    String findRoleNameByUserId(@Param("userId") Long userId);
}
