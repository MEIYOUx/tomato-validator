package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.LengthValidator;

import java.lang.annotation.*;

/**
 * 表示必须符合长度限制。
 * 对应的验证器：{@link LengthValidator}。
 * @author iuhay
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = LengthValidator.class)
public @interface Length {

    /**
     * 验证失败的错误代码
     * @return 错误代码
     */
    int code() default 400;

    /**
     * 验证失败的错误消息
     * @return 错误消息
     */
    String message() default "长度不在范围内";

    /**
     * 最小长度
     * @return 最小长度
     */
    long min();

    /**
     * 最大长度
     * @return 最大长度
     */
    long max();
}
