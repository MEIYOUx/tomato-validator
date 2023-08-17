package com.xihongshi.validator.core;

import java.lang.annotation.Annotation;

/**
 * 定义创建验证器的方法，根据传入的注解类型创建出对应的验证器。
 * @see com.xihongshi.validator.constraints.Constraints
 * @author iuhay
 */
public interface ValidatorFactory {

    /**
     * 根据注解的类型创建验证器。
     * @param annotationType 注解类型
     * @return 注解类型对应的验证器实例
     */
    Validator createValidator(Class<? extends Annotation> annotationType);
}
