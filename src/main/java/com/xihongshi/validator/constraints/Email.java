package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.EmailValidator;

import java.lang.annotation.*;

/**
 * 表示必须符合邮箱格式，提供了默认的正则规则，也支持指定正则规则。
 * 对应的验证器：{@link EmailValidator}。
 * @author iuhay
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = EmailValidator.class)
public @interface Email {

    /**
     * 验证失败的错误代码
     * @return 错误代码
     */
    int code() default 400;

    /**
     * 验证失败的错误消息
     * @return 错误消息
     */
    String message() default "邮箱格式不正确";

    /**
     * 正则规则，默认提供
     * @return 正则规则
     */
    String regex() default "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
}
