package com.eugene.sumarry.permission.service;

import com.eugene.sumarry.permission.model.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> fetchUserRoles(Long userId);

    void deleteUserRoles(Long userId);
}
