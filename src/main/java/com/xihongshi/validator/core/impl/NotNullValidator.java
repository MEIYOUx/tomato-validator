package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

import java.util.Objects;

public class NotNullValidator implements Validator {

    @Override
    public boolean validate(Object object) {
        return Objects.nonNull(object);
    }
}
