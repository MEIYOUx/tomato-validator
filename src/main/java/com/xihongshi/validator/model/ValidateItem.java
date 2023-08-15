package com.xihongshi.validator.model;

import java.lang.annotation.Annotation;

public class ValidateItem {

    private final Object object;
    private final Annotation annotation;
    private final Integer code;
    private final String message;

    public ValidateItem(Object object, Annotation annotation, Integer code, String message) {
        this.object = object;
        this.annotation = annotation;
        this.code = code;
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public Annotation getAnnotation() {
        return annotation;
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
                ", annotation=" + annotation +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
