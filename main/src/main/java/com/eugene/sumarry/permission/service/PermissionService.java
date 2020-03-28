package com.eugene.sumarry.permission.service;

import com.eugene.sumarry.permission.model.Permission;

public interface PermissionService extends BaseService<Permission, Long> {

    Permission create(Permission permission);


}
