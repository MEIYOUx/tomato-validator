package com.xihongshi.validator.exception;

/**
 * 验证异常，包含了：错误代码、错误消息。
 * 当验证不通过时应该抛出该类型异常。
 * @author iuhay
 */
public class ValidateException extends RuntimeException {

    private final Integer code;
    private final String message;

    public ValidateException(Integer code, String message) {
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
