package com.finalproject.service;

import com.finalproject.entity.Role;

public interface RoleService {
    void save(Role tempRole);
    String findRoleNameByUserId(Long userId);
}
