package com.xihongshi.validator.core;

import com.xihongshi.validator.exception.AnnotationException;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 定义验证对象是否通过的方法。
 * @author iuhay
 */
public interface Validator {

    /**
     * 验证对象是否符合注解的约束。
     * @param object     需要验证的对象
     * @param annotation 约束注解
     * @return true：通过验证；false：不通过验证。
     */
    boolean validate(Object object, Annotation annotation);

    /**
     * 检查注解的类型。
     * 在 validate(object, annotation) 进行验证前，应该检查注解的类型是否符合预期。
     * @param annotation 需要检查的注解
     * @param type       注解类型
     * @throws AnnotationException 如果不是此类型的注解，则抛出异常。
     *                             如果传入的注解 或 注解类型 为 null，则抛出异常。
     */
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
