package com.xihongshi.validator.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 注解工具类，包含了判断是否被某个类型的注解标注的一系列方法，在此对于直接标注和间接标注作出解释。
 * 直接标注是什么意思呢？假设定义如下注解：
 * <pre>
 * {@code
 *     @Ducoment
 *     public @interface Valid {
 *     }
 * }
 * </pre>
 * 现在将@Valid标注在如下方法中的参数上：
 * <pre>
 * {@code
 *     public String hello(@Valid User user) {
 *         return "Hello, " + user;
 *     }
 * }
 * </pre>
 * 此时对于 hello 方法的参数 User 来说，@Valid 就是直接标注，因此：
 * <pre>
 * {@code
 *     AnnotationUtil.hasDirectAnnotation(userParameter, Valid.class) == true;
 * }
 * </pre>
 * 间接标注是什么意思呢？假设定义如下注解：
 * <pre>
 * {@code
 *     @Ducoment
 *     public @interface Valid {
 *     }
 * }
 * </pre>
 * 现在将@Valid标注在如下方法中的参数上：
 * <pre>
 * {@code
 *     public String hello(@Valid User user) {
 *         return "Hello, " + user;
 *     }
 * }
 * </pre>
 * 此时对于 hello 方法的参数 User 来说，@Document 就是间接标注，因此：
 * <pre>
 * {@code
 *     AnnotationUtil.hasAnnotation(userParameter, Document.class) == true;
 * }
 * </pre>
 * @author iuhay
 */
public class AnnotationUtil {

    /**
     * 判断目标参数是否被某个类型的注解直接标注。
     * 如果传入的目标参数或注解类型为 null，则表示目标参数没有被该类型的注解直接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param parameter      目标参数
     * @param annotationType 注解类型
     * @return true：传入的目标参数被该类型的注解直接标注；false：传入的目标参数没有被该类型的注解直接标注。
     *               如果传入的目标参数或注解类型为 null，则返回 false。
     */
    public static boolean hasDirectAnnotation(Parameter parameter, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(parameter) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(parameter.getAnnotation(annotationType));
    }

    /**
     * 判断目标参数是否被某个类型的注解直接或间接标注。
     * 如果传入的目标参数或注解类型为 null，则表示目标参数没有被该类型的注解直接或间接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param parameter      目标参数
     * @param annotationType 注解类型
     * @return true：传入的目标参数被该类型的注解直接或间接标注；false：传入的目标参数没有被该类型的注解直接或间接标注。
     *               如果传入的目标参数或注解类型为 null，则返回 false。
     */
    public static boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(parameter) || Objects.isNull(annotationType)) {
            return false;
        }
        if (hasDirectAnnotation(parameter, annotationType)) {
            return true;
        }
        for (Annotation annotation : parameter.getAnnotations()) {
            if (hasAnnotation(annotation, annotationType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断目标注解是否被某个类型的注解直接标注。
     * 如果传入的目标注解或注解类型为 null，则表示目标注解没有被该类型的注解直接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param annotation     目标注解
     * @param annotationType 注解类型
     * @return true：传入的目标注解被该类型的注解直接标注；false：传入的目标注解没有被该类型的注解直接标注。
     *               如果传入的目标注解或注解类型为 null，则返回 false。
     */
    public static boolean hasDirectAnnotation(Annotation annotation, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotation) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(annotation.annotationType().getAnnotation(annotationType));
    }

    /**
     * 判断目标注解是否被某个类型的注解直接或间接标注。
     * 如果传入的目标注解或注解类型为 null，则表示目标注解没有被该类型的注解直接或间接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param annotation     目标注解
     * @param annotationType 注解类型
     * @return true：传入的目标注解被该类型的注解直接或间接标注；false：传入的目标注解没有被该类型的注解直接或间接标注。
     *               如果传入的目标注解或注解类型为 null，则返回 false。
     */
    public static boolean hasAnnotation(Annotation annotation, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotation) || Objects.isNull(annotationType)) {
            return false;
        }

        Class<? extends Annotation> annotationClass = annotation.getClass();
        if (hasDirectAnnotation(annotation, annotationType)) {
            return true;
        }

        for (Annotation subAnnotation : annotationClass.getAnnotations()) {
            if (hasAnnotation(subAnnotation, annotationType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断目标对象的类型是否被某个类型的注解直接标注。
     * 如果传入的目标对象或注解类型为 null，则表示目标对象没有被该类型的注解直接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param object         目标对象
     * @param annotationType 注解类型
     * @return true：传入的目标对象的类型被该类型的注解直接标注；false：传入的目标对象的类型没有被该类型的注解直接标注。
     *               如果传入的目标对象或注解类型为 null，则返回 false。
     */
    public static boolean hasDirectAnnotation(Object object, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(object) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(object.getClass().getAnnotation(annotationType));
    }

    /**
     * 判断目标对象的类型是否被某个类型的注解直接或间接标注。
     * 如果传入的目标对象或注解类型为 null，则表示目标对象没有被该类型的注解直接或间接标注。
     * 关于直接或间接标注请参考：{@link AnnotationUtil}
     * @param object         目标对象
     * @param annotationType 注解类型
     * @return true：传入的目标对象的类型被该类型的注解直接或间接标注；false：传入的目标对象的类型没有被该类型的注解直接或间接标注。
     *               如果传入的目标对象或注解类型为 null，则返回 false。
     */
    public static boolean hasAnnotation(Object object, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(object) || Objects.isNull(annotationType)) {
            return false;
        }

        if (hasDirectAnnotation(object, annotationType)) {
            return true;
        }

        for (Annotation annotation : object.getClass().getAnnotations()) {
            if (hasAnnotation(annotation, annotationType)) {
                return true;
            }
        }

        return false;
    }
}
