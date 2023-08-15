package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.MaxValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = MaxValidator.class)
public @interface Max {

    int code() default 400;
    String message() default "大于最大值";
    long value();
}
