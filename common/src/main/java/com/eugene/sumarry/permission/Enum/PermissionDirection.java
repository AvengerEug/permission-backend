package com.eugene.sumarry.permission.Enum;

public enum PermissionDirection implements BaseEnum {

    BACKEND(0), FRONTEND(1);

    private int value;


    PermissionDirection(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public static PermissionDirection int2Enum(int value) {
        PermissionDirection[] permissionDirections = PermissionDirection.values();
        for (PermissionDirection permissionDirection : permissionDirections) {
            if (permissionDirection.getValue() == value) return permissionDirection;
        }

        return null;
    }

}
