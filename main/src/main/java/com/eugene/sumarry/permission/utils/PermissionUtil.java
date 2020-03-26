package com.eugene.sumarry.permission.utils;

import com.eugene.sumarry.permission.model.Permission;
import com.eugene.sumarry.permission.model.RolePermission;
import com.eugene.sumarry.permission.model.UserRole;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class PermissionUtil {

    public static List<Permission> traversal(
            List<UserRole> userRoles,
            Consumer<List<RolePermission>> rolePermissionConsumer,
            Consumer<List<Permission>> permissionConsumer) {

        List<Permission> permissions = new ArrayList<>();
        List<RolePermission> rolePermissions = new ArrayList<>();
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }

        for (UserRole userRole : userRoles) {
            List<RolePermission> rolePermissionsInner = userRole.getRole().getRolePermissions();
            rolePermissions.addAll(rolePermissionsInner);

            if (!CollectionUtils.isEmpty(rolePermissions)) {
                for (RolePermission rolePermission : rolePermissions) {
                    permissions.add(rolePermission.getPermission());
                }
            }

        }

        if (!ObjectUtils.isEmpty(rolePermissionConsumer)) rolePermissionConsumer.accept(rolePermissions);
        if (!ObjectUtils.isEmpty(permissionConsumer)) permissionConsumer.accept(permissions);

        return permissions;
    }

}
