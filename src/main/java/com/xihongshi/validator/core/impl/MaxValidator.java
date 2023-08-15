package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Max;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.ObjectMeasuringUtil;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Objects;

public class MaxValidator implements Validator {

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
