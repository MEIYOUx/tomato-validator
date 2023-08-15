package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Length;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.util.ObjectMeasuringUtil;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class LengthValidator implements Validator {

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
