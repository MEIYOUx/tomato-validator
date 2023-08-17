package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Min;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.ObjectMeasuringUtil;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 最小值验证器，{@link Validator} 的一个实现。
 * 配合 {@link Min} 使用，提供对最小值的验证。
 * @see Min
 * @author iuhay
 */
public class MinValidator implements Validator {

    /**
     * 验证对象是否不小于最小值。
     * @param object     需要验证的对象
     * @param annotation 约束注解，类型必须为 {@link Min} 类型
     * @return true：不小于最小值；false：小于最小值。
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        checkAnnotation(annotation, Min.class);
        Min min = (Min) annotation;
        BigDecimal value = ObjectMeasuringUtil.numberValue(object);
        if (Objects.isNull(value)) {
            throw new ValidateException(min.code(), String.format(
                    "无法解析{ %s }的数值",
                    object
            ));
        }
        return value.compareTo(new BigDecimal(min.value())) >= 0;
    }
}
