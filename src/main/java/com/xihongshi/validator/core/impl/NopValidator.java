package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;

public class NopValidator implements Validator {
    @Override
    public boolean validate(Object object, Annotation annotation) {
        return true;
    }
}
