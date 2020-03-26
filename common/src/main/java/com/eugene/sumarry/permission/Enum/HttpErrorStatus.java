package com.eugene.sumarry.permission.Enum;

public enum HttpErrorStatus {


    /**
     * UserRole模块:       代号100
     * controller层        代码100
     * (service层          代号200     dao层   代号: 300  以此类推
     * 业务代码:       代码0001  依次递增
     */
    MISMATCH_PERMISSION_UPDATED("1002000001", "更新的权限不匹配")

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
