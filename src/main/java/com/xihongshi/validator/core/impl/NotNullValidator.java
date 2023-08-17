package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class NotNullValidator implements Validator {

    @Override
    public boolean validate(Object object, Annotation annotation) {
        return Objects.nonNull(object);
    }
}
