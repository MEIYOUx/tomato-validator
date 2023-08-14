package com.xihongshi.validator;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TomatoValidatorAutoConfiguration.class)
public @interface EnableTomatoValidator {
}
