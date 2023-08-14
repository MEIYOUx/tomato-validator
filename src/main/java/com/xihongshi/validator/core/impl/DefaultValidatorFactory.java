package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Constraints;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.core.ValidatorFactory;
import com.xihongshi.validator.exception.InitValidatorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultValidatorFactory implements ValidatorFactory {

    private final Map<Class<? extends Annotation>, Validator> validatorCache = new ConcurrentHashMap<>();

    @Override
    public Validator createValidator(Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotationType.getAnnotation(Constraints.class))) {
            throw new IllegalArgumentException(annotationType + ": 仅支持被约束注解标注的注解类型，请参考 " + Constraints.class);
        }

        Validator validator = validatorCache.get(annotationType);
        if (Objects.nonNull(validator)) {
            return validator;
        }

        try {
            validator = initValidator(annotationType);
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new InitValidatorException(e);
        }

        validatorCache.put(annotationType, validator);
        return validator;
    }

    private Validator initValidator(Class<? extends Annotation> annotationType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constraints constraints = annotationType.getAnnotation(Constraints.class);
        if (Objects.isNull(constraints)) {
            throw new IllegalArgumentException(annotationType + ": 仅支持被约束注解标注的注解类型，请参考 " + Constraints.class);
        }

        Class<? extends Validator> validatorType = constraints.validatorType();
        return validatorType.getDeclaredConstructor().newInstance();
    }
}
