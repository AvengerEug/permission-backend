package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.permission.Enum.HttpErrorStatus;
import com.eugene.sumarry.permission.dao.RolePermissionDao;
import com.eugene.sumarry.permission.model.Permission;
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
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private static final Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Override
    public void batchCreate(List<RolePermission> permissions) {
        PermissionUtil.traversal(permissions, (permission) -> {
            Permission permission4DB = permissionService.get(permission.getPermissionId());
            if (ObjectUtils.isEmpty(permission4DB)) {
                logger.warn(HttpErrorStatus.PERMISSION_NOT_EXISTED.getMessage());
                Assert.stopProcess(
                        HttpErrorStatus.PERMISSION_NOT_EXISTED.getCode(),
                        HttpErrorStatus.PERMISSION_NOT_EXISTED.getMessage());
            }

            // 需要验证 permission关联的角色是否存在
            Role role = roleService.get(permission.getRoleId());
            if (ObjectUtils.isEmpty(role)) {
                logger.warn(HttpErrorStatus.ROLE_EXISTED_4_RP.getMessage());
                Assert.stopProcess(
                        HttpErrorStatus.ROLE_EXISTED_4_RP.getCode(),
                        HttpErrorStatus.ROLE_EXISTED_4_RP.getMessage());
            }
        });

        rolePermissionDao.batchInsert(permissions);
    }
}
