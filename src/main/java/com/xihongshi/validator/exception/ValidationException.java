package com.xihongshi.validator.exception;

public class ValidationException extends RuntimeException {

    private final Integer code;
    private final String message;

    public ValidationException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
