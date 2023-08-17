package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Length;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.ObjectMeasuringUtil;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 长度验证器，{@link Validator} 的一个实现。
 * 配合 {@link Length} 使用，提供对长度的验证。
 * @see Length
 * @author iuhay
 */
public class LengthValidator implements Validator {

    /**
     * 验证对象是否符合长度限制。
     * @param object     需要验证的对象
     * @param annotation 约束注解，必须为 {@link Length} 类型
     * @return true：符合长度限制；false：不符合长度限制。
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        checkAnnotation(annotation, Length.class);
        Length length = (Length) annotation;
        Long measure = ObjectMeasuringUtil.measureLength(object);
        if (Objects.isNull(measure)) {
            throw new ValidateException(length.code(), String.format(
                    "无法测量{ %s }的长度",
                    object
            ));
        }
        return length.min() <= measure && measure <= length.max();
    }
}
