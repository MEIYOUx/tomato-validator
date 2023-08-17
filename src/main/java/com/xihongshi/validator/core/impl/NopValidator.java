package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;

/**
 * 空的验证器，{@link Validator} 的一个实现。
 * 当不需要进行任何验证时，可以使用该验证器。
 * 也用于在 {@link com.xihongshi.validator.core.ValidateAspect} 创建验证器时发生错误，返回的空的验证器。
 * @see com.xihongshi.validator.core.ValidateAspect
 * @author iuhay
 */
public class NopValidator implements Validator {

    /**
     * 空的验证，始终返回 true。
     * @param object     需要验证的对象
     * @param annotation 约束注解
     * @return true
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        return true;
    }
}
