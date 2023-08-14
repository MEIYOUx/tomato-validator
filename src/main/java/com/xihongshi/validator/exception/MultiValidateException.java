package com.xihongshi.validator.exception;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
