package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.PatternValidator;

import java.lang.annotation.*;

/**
 * 表示必须匹配正则规则。
 * 对应的验证器：{@link PatternValidator}。
 * @author iuhay
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = PatternValidator.class)
public @interface Pattern {

    /**
     * 验证失败的错误代码
     * @return 错误代码
     */
    int code() default 400;

    /**
     * 验证失败的错误消息
     * @return 错误消息
     */
    String message() default "不匹配";

    /**
     * 正则规则
     * @return 正则规则
     */
    String regex();
}
