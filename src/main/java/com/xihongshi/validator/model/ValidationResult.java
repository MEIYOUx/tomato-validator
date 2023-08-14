package com.xihongshi.validator.model;

import com.xihongshi.validator.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationException> exceptionList = new ArrayList<>(0);

    public void addException(ValidationException e) {
        exceptionList.add(e);
    }

    public boolean isPassed() {
        return exceptionList.size() == 0;
    }

    public List<ValidationException> getExceptionList() {
        return Collections.unmodifiableList(exceptionList);
    }
}
