package com.xthore.common.exception;

public class BusinessException extends RuntimeException {
    private final int code;
    private final String status;
    private final Object reason;

    public BusinessException(int code, String status, Object reason) {
        super(status);
        this.code = code;
        this.status = status;
        this.reason = reason;
    }

    public BusinessException(int code, String status) {
        this(code, status, null);
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Object getReason() {
        return reason;
    }
}
