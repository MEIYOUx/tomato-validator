package com.xihongshi.validator.core;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public interface Validator {

    boolean validate(Object object, Annotation annotation);

    default void check(Object object, Annotation annotation, Class<? extends Annotation> type) {
        Assert.notNull(object, "对象不能为空");
        Assert.notNull(annotation, "注解不能为空");
        Assert.notNull(type, "注解类型不能为空");
        if (!type.isInstance(object)) {
            throw new IllegalArgumentException(String.format(
                    "注解类型不匹配，需要( %s )，传入( %s )",
                    type.getName(),
                    annotation.annotationType().getName()
            ));
        }
    }
}
