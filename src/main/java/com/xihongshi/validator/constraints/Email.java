package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.EmailValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = EmailValidator.class)
public @interface Email {

    int code() default 400;
    String message() default "邮箱格式不正确";
}
