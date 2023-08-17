package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Pattern;
import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 正则规则验证器，{@link Validator} 的一个实现。
 * 配合 {@link Pattern} 使用，提供对正则规则的验证。
 * @see Pattern
 * @author iuhay
 */
public class PatternValidator implements Validator {

    /**
     * 验证对象是否匹配正则规则。
     * @param object     需要验证的对象
     * @param annotation 约束注解，类型必须为 {@link Pattern} 类型
     * @return true：匹配正则规则；false：不匹配正则规则。
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        if (Objects.isNull(object)) {
            return false;
        }
        checkAnnotation(annotation, Pattern.class);
        Pattern pattern = (Pattern) annotation;
        return java.util.regex.Pattern.matches(pattern.regex(), String.valueOf(object));
    }
}
