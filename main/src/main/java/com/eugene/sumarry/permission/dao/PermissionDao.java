package com.eugene.sumarry.permission.dao;

import com.eugene.sumarry.permission.model.Permission;

public interface PermissionDao extends BaseDao<Permission, Long> {

    Permission getByKey(String permissionKey);
}
