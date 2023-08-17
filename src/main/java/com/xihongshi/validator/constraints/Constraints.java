package com.xihongshi.validator.constraints;

import com.xihongshi.validator.core.Validator;
import java.lang.annotation.*;

/**
 * 表示被约束的注解，使用该注解时需要传入验证器的类型。
 * 注意：标注了该注解的注解需要提供 int code() 和 String message()，
 *      否则对参数验证失败时，将采用 {@link com.xihongshi.validator.TomatoValidatorProperties} 默认的code、message！
 *
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Constraints {

    String CODE = "code";
    String MESSAGE = "message";

    Class<? extends Validator> validatorType();
}
