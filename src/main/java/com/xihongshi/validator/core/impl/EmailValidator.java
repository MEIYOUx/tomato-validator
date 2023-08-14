package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

import java.util.regex.Pattern;

public class EmailValidator implements Validator {

    private static final String REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    @Override
    public boolean validate(Object object) {
        return object instanceof CharSequence && Pattern.matches(REGEX, (CharSequence) object);
    }
}
