package com.xihongshi.validator.exception;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 多验证异常，其中包含了验证异常 {@link ValidateException} 的List。
 * 当关闭快速验证时，验证结果将存在多个验证异常 {@link ValidateException}，此时将会抛出多验证异常。
 * @author iuhay
 */
public class MultiValidateException extends RuntimeException {

    private final Integer code;
    private final String message;
    private final List<ValidateException> exceptionList;

    public MultiValidateException(Integer code, String message, List<ValidateException> exceptionList) {
        this.code = code;
        this.message = message;
        this.exceptionList = Objects.isNull(exceptionList) ? Collections.emptyList() : exceptionList;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 获取验证异常的 List
     * @return 验证异常的 List
     */
    public List<ValidateException> getExceptionList() {
        return exceptionList;
    }

    @Override
    public String toString() {
        return "MultiValidateException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", exceptionList=" + exceptionList +
                '}';
    }
}
