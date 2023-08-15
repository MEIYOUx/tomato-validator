package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Email;
import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {

    @Override
    public boolean validate(Object object, Annotation annotation) {
        if (Objects.isNull(object)) {
            return false;
        }
        checkAnnotation(annotation, Email.class);
        Email email = (Email) annotation;
        return Pattern.matches(email.regex(), String.valueOf(object));
    }
}
