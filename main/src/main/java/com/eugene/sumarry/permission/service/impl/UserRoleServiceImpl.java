package com.eugene.sumarry.permission.service.impl;

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
}
