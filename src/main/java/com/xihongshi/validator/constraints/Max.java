package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.MaxValidator;

import java.lang.annotation.*;

/**
 * 表示必须不大于最大值。
 * 对应的验证器：{@link MaxValidator}。
 * @author iuhay
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = MaxValidator.class)
public @interface Max {

    /**
     * 验证失败的错误代码
     * @return 错误代码
     */
    int code() default 400;

    /**
     * 验证失败的错误消息
     * @return 错误消息
     */
    String message() default "大于最大值";

    /**
     * 最大值
     * @return 最大值
     */
    long value();
}
