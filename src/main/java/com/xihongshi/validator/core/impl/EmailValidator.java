package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Email;
import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {

    @Override
    public boolean validate(Object object, Annotation annotation) {
        check(object, annotation, Email.class);
        Email email = (Email) annotation;
        return object instanceof CharSequence && Pattern.matches(email.regex(), (CharSequence) object);
    }
}
