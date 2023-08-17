package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.MinValidator;

import java.lang.annotation.*;

/**
 * 表示必须不小于最小值。
 * 对应的验证器：{@link MinValidator}。
 * @author iuhay
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = MinValidator.class)
public @interface Min {

    /**
     * 验证失败的错误代码
     * @return 错误代码
     */
    int code() default 400;

    /**
     * 验证失败的错误消息
     * @return 错误消息
     */
    String message() default "小于最小值";

    /**
     * 最小值
     * @return 最小值
     */
    long value();
}
