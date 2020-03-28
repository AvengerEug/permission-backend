package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.permission.Enum.HttpErrorStatus;
import com.eugene.sumarry.permission.dao.PermissionDao;
import com.eugene.sumarry.permission.model.Permission;
import com.eugene.sumarry.permission.service.PermissionService;
import com.eugene.sumarry.permission.utils.Assert;
import com.eugene.sumarry.permission.utils.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission create(Permission permission) {

        permissionDao.insert(permission);

        return permission;
    }

    @Override
    public void batchCreate(List<Permission> permissions) {

        PermissionUtil.traversal(permissions, (permission) -> {
            Permission permissionInner = permissionDao.getByKey(permission.getPermissionKey());
            if (!ObjectUtils.isEmpty(permissionInner)) {
                Assert.stopProcess(
                        HttpErrorStatus.PERMISSION_EXISTED.getCode(),
                        HttpErrorStatus.PERMISSION_EXISTED.getMessage()
                );
            }
        });

        permissionDao.batchInsert(permissions);
    }

    @Override
    public Permission get(Long aLong) {
        return permissionDao.getById(aLong);
    }
}
