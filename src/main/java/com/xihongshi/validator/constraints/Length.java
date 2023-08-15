package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.LengthValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = LengthValidator.class)
public @interface Length {

    int code() default 400;
    String message() default "长度不在范围内";
    long min();
    long max();
}
