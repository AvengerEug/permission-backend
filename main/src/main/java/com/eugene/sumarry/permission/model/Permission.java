package com.eugene.sumarry.permission.model;

import com.eugene.sumarry.permission.Enum.PermissionDirection;
import com.eugene.sumarry.permission.Enum.PermissionType;

import javax.validation.constraints.NotBlank;

public class Permission extends BaseModel {

    private Long permissionId;
    private String permissionName;
    private PermissionDirection permissionDirection;
    private Long parentPermissionId;
    private PermissionType permissionType;

    @NotBlank
    private String permissionKey;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public PermissionDirection getPermissionDirection() {
        return permissionDirection;
    }

    public void setPermissionDirection(PermissionDirection permissionDirection) {
        this.permissionDirection = permissionDirection;
    }

    public Long getParentPermissionId() {
        return parentPermissionId;
    }

    public void setParentPermissionId(Long parentPermissionId) {
        this.parentPermissionId = parentPermissionId;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }
}
