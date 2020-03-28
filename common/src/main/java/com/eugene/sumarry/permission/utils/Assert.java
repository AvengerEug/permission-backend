package com.eugene.sumarry.permission.utils;

import com.eugene.sumarry.permission.exception.StopProcessRuntimeException;
import org.apache.commons.lang.StringUtils;

public final class Assert {

    public static void stopProcess(String code, String message) {
        throw new StopProcessRuntimeException(code, message);
    }

    public static void stopProcess(String message) {
        throw new StopProcessRuntimeException(message);
    }

    public static void stopProcess() {
        stopProcess(StringUtils.EMPTY);
    }

}
