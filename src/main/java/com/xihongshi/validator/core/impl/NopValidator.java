package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.core.Validator;

public class NopValidator implements Validator {
    @Override
    public boolean validate(Object object) {
        return true;
    }
}
