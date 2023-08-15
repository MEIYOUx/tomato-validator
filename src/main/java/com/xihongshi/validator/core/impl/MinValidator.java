package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Min;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.TypeUtil;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Objects;

public class MinValidator implements Validator {

    @Override
    public boolean validate(Object object, Annotation annotation) {
        checkAnnotation(annotation, Min.class);
        Min min = (Min) annotation;
        BigDecimal value = TypeUtil.numberValue(object);
        if (Objects.isNull(value)) {
            throw new ValidateException(min.code(), String.format(
                    "无法解析{ %s }的数值",
                    object
            ));
        }
        return value.compareTo(new BigDecimal(min.value())) >= 0;
    }
}
