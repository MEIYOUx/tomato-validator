package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.MinValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = MinValidator.class)
public @interface Min {

    int code() default 400;
    String message() default "小于最小值";
    long value();
}
