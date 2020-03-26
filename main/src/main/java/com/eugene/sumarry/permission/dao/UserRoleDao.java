package com.eugene.sumarry.permission.dao;

import com.eugene.sumarry.permission.model.UserRole;

import java.util.List;

public interface UserRoleDao extends BaseDao<UserRole, Long> {

    List<UserRole> findUserRoleByUserId(Long userId);

    void deleteByUserId(Long userId);

}
