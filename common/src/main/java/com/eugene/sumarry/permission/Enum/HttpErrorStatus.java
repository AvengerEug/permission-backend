package com.eugene.sumarry.permission.Enum;

public enum HttpErrorStatus {


    /**
     * UserRole模块:       代号100
     * controller层        代码100
     * (service层          代号200     dao层   代号: 300  以此类推
     * dao层               代号300
     * util层              代号400
     * 业务代码:       代码0001  依次递增
     */
    MISMATCH_PERMISSION_UPDATED("1002000001", "更新的权限不匹配"),


    /**
     * Role模块: 代号: 300
     */
    ROLE_EXISTED("3003000001", "角色已存在，创建失败"),
    ROLE_ASSOCIATE_NOT_EXISTEND_PERMISSION("3003000002", "角色关联到不存在的权限"),

    /**
     * RolePermission模块: 代号400
     */
    PERMISSION_NOT_EXISTED("4001000001", "权限不存在, 无法关联角色"),
    ROLE_EXISTED_4_RP("4001000002", "角色已存在，无法关联角色"),

    /**
     * Permission模块:  代号500
     */
    PERMISSION_EXISTED("5001000001", "权限已存在, 无法创建"),
    NOT_ACCESS_API_PERMISSION("5004000002", "无权限访问api"),

    ;


    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    HttpErrorStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
