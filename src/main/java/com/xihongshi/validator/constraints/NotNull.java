package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.impl.NotNullValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraints(validatorType = NotNullValidator.class)
public @interface NotNull {

    int code() default 400;

    String message() default "参数不能为空";
}
