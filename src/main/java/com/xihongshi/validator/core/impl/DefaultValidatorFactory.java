package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Constraints;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.core.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的验证器工厂，实现 {@link ValidatorFactory} 接口，提供根据注解类型创建对应的验证器。
 * @author iuhay
 */
public class DefaultValidatorFactory implements ValidatorFactory {

    private final Logger logger = LoggerFactory.getLogger(DefaultValidatorFactory.class);

    /**
     * 用于对验证器进行缓存，key：注解的类型，value：验证器
     */
    private final Map<Class<? extends Annotation>, Validator> validatorCache = new ConcurrentHashMap<>();
    private static final Validator NOP_VALIDATOR = new NopValidator();

    /**
     * 创建验证器，保证不会返回 null。当创建验证器失败时，将会返回空的验证器 {@link NopValidator}。
     * 在第一次创建该注解类型对应的验证器时，将会实例化验证器，然后将其放入缓存中。
     * 后续创建同类型注解对应的验证器时，则从缓存中直接获取。
     * @param annotationType 注解类型
     * @return 注解类型所对应的验证器，保证不会返回 null。当创建验证器失败时，将会返回空的验证器 {@link NopValidator}。
     */
    @Override
    public Validator createValidator(Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotationType.getAnnotation(Constraints.class))) {
            logger.error("{}: 仅支持被约束注解标注的注解类型，请参考 {}", annotationType, Constraints.class);
            return NOP_VALIDATOR;
        }

        Validator validator = validatorCache.get(annotationType);
        if (Objects.nonNull(validator)) {
            return validator;
        }

        validator = safetyInitValidator(annotationType);
        validatorCache.put(annotationType, validator);
        return validator;
    }

    /**
     * 安全地初始化验证器，保证不会返回 null。如果初始化时发生异常，则会返回空的验证器 {@link NopValidator}。
     * @param annotationType 注解类型
     * @return 安全地初始化验证器，保证不会返回 null。如果初始化时发生异常，则会返回空的验证器 {@link NopValidator}。
     */
    private Validator safetyInitValidator(Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotationType)) {
            return NOP_VALIDATOR;
        }
        Constraints constraints = annotationType.getAnnotation(Constraints.class);
        if (Objects.isNull(constraints)) {
            logger.error("{}: 仅支持被约束注解标注的注解类型，请参考 {}", annotationType, Constraints.class);
            return NOP_VALIDATOR;
        }

        Class<? extends Validator> validatorType = constraints.validatorType();
        try {
            return validatorType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("初始化验证器失败！", e);
            return NOP_VALIDATOR;
        }
    }
}
