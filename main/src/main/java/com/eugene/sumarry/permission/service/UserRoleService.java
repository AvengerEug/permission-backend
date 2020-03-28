package com.eugene.sumarry.permission.service;

import com.eugene.sumarry.permission.model.UserRole;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole, Long> {

    List<UserRole> fetchUserRoles(Long userId);

    void deleteUserRoles(Long userId);

    List<UserRole> findAll();

    void updateUserRoles(List<UserRole> userRoleList, Long updatedTarget);

    void addUserRoles(List<UserRole> userRoles, Long addedTarget);
}
