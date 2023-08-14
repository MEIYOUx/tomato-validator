package com.xihongshi.validator.core;

import java.lang.annotation.Annotation;

public interface ValidatorFactory {

    Validator createValidator(Class<? extends Annotation> annotationType);
}
