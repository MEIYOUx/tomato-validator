package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Pattern;
import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;

public class PatternValidator implements Validator {

    @Override
    public boolean validate(Object object, Annotation annotation) {
        checkAnnotation(annotation, Pattern.class);
        Pattern pattern = (Pattern) annotation;
        return java.util.regex.Pattern.matches(pattern.regex(), String.valueOf(object));
    }
}
