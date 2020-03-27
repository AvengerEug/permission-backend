package com.eugene.sumarry.permission.model;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Role extends BaseModel {

    private Long roleId;

    @NotBlank
    private String roleName;

    private List<RolePermission> rolePermissions;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
