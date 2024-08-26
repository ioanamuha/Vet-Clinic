package com.finalproject.service;

import com.finalproject.entity.Role;
import com.finalproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role tempRole) {
        roleRepository.save(tempRole);
    }

    @Override
    public String findRoleNameByUserId(Long userId) {
        return roleRepository.findRoleNameByUserId(userId);
    }
}
