package com.eugene.sumarry.permission.exception;

public class StopProcessRuntimeException extends RuntimeException {

    public String code;

    public StopProcessRuntimeException() {
    }

    public StopProcessRuntimeException(String message) {
        super(message);
    }

    public StopProcessRuntimeException(String code, String message) {
        super(message);
    }
}
