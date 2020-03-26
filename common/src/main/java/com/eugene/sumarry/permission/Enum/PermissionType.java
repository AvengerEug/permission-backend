package com.eugene.sumarry.permission.Enum;

public enum PermissionType implements BaseEnum {

    HTML(0), API(1);

    private int value;

    PermissionType(int value) {
        this.value = value;
    }


    @Override
    public int getValue() {
        return this.value;
    }

    public static PermissionType int2Enum(int value) {
        PermissionType[] permissionTypes = PermissionType.values();
        for (PermissionType permissionType : permissionTypes) {
            if (permissionType.value == value) return permissionType;
        }

        return null;
    }
}
