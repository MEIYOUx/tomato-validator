package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.PatternValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = PatternValidator.class)
public @interface Pattern {

    int code() default 400;
    String message() default "不匹配";
    String regex();
}
