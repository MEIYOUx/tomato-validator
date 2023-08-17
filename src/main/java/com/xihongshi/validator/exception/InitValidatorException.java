package com.xihongshi.validator.exception;

/**
 * 初始化验证器异常，在对验证器进行初始化时出现错误则应该抛出该类型异常。
 * @author iuhay
 */
public class InitValidatorException extends RuntimeException {

    public InitValidatorException(String message) {
        super(message);
    }

    public InitValidatorException(Throwable cause) {
        super(cause);
    }
}
