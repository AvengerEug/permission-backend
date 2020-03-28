package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.permission.anno.ExceptionTransactional;
import com.eugene.sumarry.permission.dao.UserRoleDao;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> fetchUserRoles(Long userId) {
        return userRoleDao.findUserRoleByUserId(userId);
    }

    @Override
    public void deleteUserRoles(Long userId) {
        userRoleDao.deleteByUserId(userId);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleDao.getAll();
    }

    @Override
    @ExceptionTransactional
    public void updateUserRoles(List<UserRole> userRoleList, Long updatedTarget) {
        userRoleDao.deleteByUserId(updatedTarget);
        userRoleDao.batchInsert(userRoleList);
    }

    @Override
    public void addUserRoles(List<UserRole> userRoles, Long addedTarget) {
        // 允许有多个同样的角色, 允许这样的操作
        userRoleDao.batchInsert(userRoles);
    }

}
