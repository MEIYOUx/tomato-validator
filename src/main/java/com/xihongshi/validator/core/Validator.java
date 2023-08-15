package com.xihongshi.validator.core;

import com.xihongshi.validator.exception.AnnotationException;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public interface Validator {

    boolean validate(Object object, Annotation annotation);

    default void checkAnnotation(Annotation annotation, Class<? extends Annotation> type) {
        Assert.notNull(annotation, "注解不能为空");
        Assert.notNull(type, "注解类型不能为空");
        if (!type.isInstance(annotation)) {
            throw new AnnotationException(String.format(
                    "注解类型不匹配，需要( %s )，传入( %s )",
                    type.getName(),
                    annotation.annotationType().getName()
            ));
        }
    }
}
