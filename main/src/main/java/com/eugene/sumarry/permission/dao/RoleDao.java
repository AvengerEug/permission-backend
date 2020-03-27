package com.eugene.sumarry.permission.dao;

import com.eugene.sumarry.permission.model.Role;

public interface RoleDao extends BaseDao<Role, Long> {

    Role getByName(String name);

}
