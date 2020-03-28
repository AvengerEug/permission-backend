package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.permission.Enum.HttpErrorStatus;
import com.eugene.sumarry.permission.anno.ExceptionTransactional;
import com.eugene.sumarry.permission.dao.RoleDao;
import com.eugene.sumarry.permission.model.Role;
import com.eugene.sumarry.permission.model.RolePermission;
import com.eugene.sumarry.permission.service.PermissionService;
import com.eugene.sumarry.permission.service.RolePermissionService;
import com.eugene.sumarry.permission.service.RoleService;
import com.eugene.sumarry.permission.utils.Assert;
import com.eugene.sumarry.permission.utils.PermissionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    @ExceptionTransactional
    public Role create(Role role) {
        PermissionUtil.validate(role, (roleParam) -> {
            validateRoleExisted(roleParam);
        });

        roleDao.insert(role);

        if (!ObjectUtils.isEmpty(role.getRolePermissions())) {
            // 填充permission roleId
            for (RolePermission rolePermission : role.getRolePermissions()) {
                populateRolePermission(rolePermission, role);
            }

            rolePermissionService.batchCreate(role.getRolePermissions());
        }

        return role;
    }

    private void populateRolePermission(RolePermission rolePermission, Role role) {
        rolePermission.setRoleId(role.getRoleId());
        rolePermission.setRoleName(role.getRoleName());
    }

    @Override
    @ExceptionTransactional
    public void batchCreate(List<Role> list) {

        // 遍历role, 校验role是否存在, 以及内部携带的permission是否有权限
        PermissionUtil.traversal(list, role -> {
            validateRoleExisted(role);
        });

        // 角色在db中不存在, 则创建
        roleDao.batchInsert(list);

        for (Role role : list) {
            // 新增角色权限数据时，内部会校验权限是否存在
            for (RolePermission rolePermission : role.getRolePermissions()) {
                populateRolePermission(rolePermission, role);
            }

            rolePermissionService.batchCreate(role.getRolePermissions());
        }
    }

    private void validateRoleExisted(Role role) {
        Role roleInner = roleDao.getByName(role.getRoleName());
        if (!ObjectUtils.isEmpty(roleInner)) {
            logger.warn(HttpErrorStatus.ROLE_EXISTED.getMessage() );
            Assert.stopProcess(
                    HttpErrorStatus.ROLE_EXISTED.getCode(),
                    HttpErrorStatus.ROLE_EXISTED.getMessage());
        }
    }

    @Override
    public Role get(Long aLong) {
        return roleDao.getById(aLong);
    }
}
