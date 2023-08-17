package com.xihongshi.validator.model;

import java.lang.annotation.Annotation;

/**
 * 验证项，每个验证项中包含了：需要验证的对象、约束注解。
 * 每个需要验证的对象上可能存在多个约束注解，那么作用在这个对象上的每个约束注解都对应一个验证项。
 * 例如：
 * <pre>
 * {@code
 *     public User {
 *
 *         @NotNull(code = 400_001, message = "name can not be null.")
 *         @Min(value = 2, code = 400_002, message = "name's length must >= 2.")
 *         @Max(value = 16, code = 400_003, message = "name's length must <= 16.")
 *         private String name;
 *     }
 * }
 * 此时，有三个约束注解作用在 name 对象上，在创建验证上下文时会创建三个不同的验证项。
 * 分别是：
 * [
 *     { object = name, annotation = @NotNull },
 *     { object = name, annotation = @Min },
 *     { object = name, annotation = @Max }
 * ]
 * </pre>
 * @author iuhay
 */
public class ValidateItem {

    /**
     * 需要验证的对象
     */
    private final Object object;

    /**
     * 作用在需要验证的对象上的约束注解
     */
    private final Annotation annotation;

    public ValidateItem(Object object, Annotation annotation) {
        this.object = object;
        this.annotation = annotation;
    }

    public Object getObject() {
        return object;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
    @Override
    public String toString() {
        return "ValidationItem{" +
                "object=" + object +
                ", annotation=" + annotation +
                '}';
    }
}
