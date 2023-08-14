package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.core.impl.NopValidator;

import java.lang.annotation.*;

/**
 * 表示被约束的注解、类型或方法。
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Constraints {

    String CODE = "code";
    String MESSAGE = "message";

    Class<? extends Validator> validatorType() default NopValidator.class;
}
