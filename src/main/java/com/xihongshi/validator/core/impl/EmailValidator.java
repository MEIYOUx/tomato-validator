package com.xihongshi.validator.core.impl;

import com.xihongshi.validator.constraints.Email;
import com.xihongshi.validator.core.Validator;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 邮箱验证器，{@link Validator} 的一个实现。
 * 配合 {@link Email} 使用，提供对邮箱格式的验证。
 * @see Email
 * @author iuhay
 */
public class EmailValidator implements Validator {

    /**
     * 验证对象是否符合邮箱的格式。
     * @param object     需要验证的对象
     * @param annotation 约束注解，类型必须为 {@link Email} 类型
     * @return true：符合邮箱的格式；false：不符合邮箱的格式。
     */
    @Override
    public boolean validate(Object object, Annotation annotation) {
        if (Objects.isNull(object)) {
            return false;
        }
        checkAnnotation(annotation, Email.class);
        Email email = (Email) annotation;
        return Pattern.matches(email.regex(), String.valueOf(object));
    }
}
