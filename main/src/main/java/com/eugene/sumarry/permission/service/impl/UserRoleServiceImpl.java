package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.permission.anno.ExceptionTransactional;
import com.eugene.sumarry.permission.dao.UserRoleDao;
import com.eugene.sumarry.permission.model.RolePermission;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import com.eugene.sumarry.permission.utils.PermissionUtil;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void updateUserRole(List<UserRole> userRoleList, Long updatedTarget) {
        userRoleDao.deleteByUserId(updatedTarget);
        userRoleDao.batchInsert(userRoleList);
    }
}
