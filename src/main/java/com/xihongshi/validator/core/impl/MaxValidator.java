package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Max;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.ObjectMeasuringUtil;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 最大值验证器，{@link Validator} 的一个实现。
 * 配合 {@link Max} 使用，提供对最大值的验证。
 * @see Max
 * @author iuhay
 */
public class MaxValidator implements Validator {

    /**
     * 验证对象是否不大于最大值。
     * @param object     需要验证的对象
     * @param annotation 约束注解，类型必须为 {@link Max} 类型
     * @return true：不大于最大值；false：大于最大值。
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        checkAnnotation(annotation, Max.class);
        Max max = (Max) annotation;
        BigDecimal value = ObjectMeasuringUtil.numberValue(object);
        if (Objects.isNull(value)) {
            throw new ValidateException(max.code(), String.format(
                    "无法解析{ %s }的数值",
                    object
            ));
        }
        return value.compareTo(new BigDecimal(max.value())) <= 0;
    }
}
