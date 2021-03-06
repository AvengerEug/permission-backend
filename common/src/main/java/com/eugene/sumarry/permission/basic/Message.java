package com.eugene.sumarry.permission.basic;

import org.apache.catalina.connector.Response;

public class Message {

    public final static Integer OK = Response.SC_OK;
    private final static Integer ERROR = Response.SC_INTERNAL_SERVER_ERROR;
    private final static Integer BAD_REQUEST = Response.SC_BAD_REQUEST;

    private Integer statusCode;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    private static class SuccessMessage extends Message {
        private Object data;

        public SuccessMessage() {
            super.statusCode = OK;
        }

        public SuccessMessage(Object data) {
            super.statusCode = OK;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    private static class ErrorMessage extends Message {
        private String errorMessage;

        public ErrorMessage(String errorMessage) {
            super.statusCode = ERROR;
            this.errorMessage = errorMessage;
        }

        public ErrorMessage(Integer code, String errorMessage) {
            super.statusCode = code;
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static Message ok() {
        return new SuccessMessage();
    }

    public static Message ok(Object data) {
        return new SuccessMessage(data);
    }

    public static Message error(String message) {
        return new ErrorMessage(message);
    }

    public static Message bad(String errorMessage) {
        return new ErrorMessage(BAD_REQUEST, errorMessage);
    }


}
