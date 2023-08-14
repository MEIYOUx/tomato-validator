package com.xihongshi.validator.model;

import java.lang.annotation.Annotation;

public class ValidateItem {

    private final Object object;
    private final Class<? extends Annotation> annotationType;
    private final Integer code;
    private final String message;

    public ValidateItem(Object object, Class<? extends Annotation> annotationType, Integer code, String message) {
        this.object = object;
        this.annotationType = annotationType;
        this.code = code;
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationItem{" +
                "object=" + object +
                ", annotationType=" + annotationType +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
