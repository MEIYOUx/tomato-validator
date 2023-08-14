package com.xihongshi.validator.model;

import com.xihongshi.validator.exception.ValidateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidateResult {

    private final List<ValidateException> exceptionList = new ArrayList<>(0);

    public void addException(ValidateException e) {
        exceptionList.add(e);
    }

    public boolean isPassed() {
        return exceptionList.size() == 0;
    }

    public List<ValidateException> getExceptionList() {
        return Collections.unmodifiableList(exceptionList);
    }
}
