package com.xihongshi.validator.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class AnnotationUtil {

    public static boolean hasDirectAnnotation(Parameter parameter, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(parameter) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(parameter.getAnnotation(annotationType));
    }

    public static boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(parameter) || Objects.isNull(annotationType)) {
            return false;
        }
        if (hasDirectAnnotation(parameter, annotationType)) {
            return true;
        }
        for (Annotation annotation : parameter.getAnnotations()) {
            if (hasAnnotationByClass(annotation, annotationType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDirectAnnotationByClass(Annotation annotation, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotation) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(annotation.annotationType().getAnnotation(annotationType));
    }

    public static boolean hasDirectAnnotationByClass(Object object, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(object) || Objects.isNull(annotationType)) {
            return false;
        }
        return Objects.nonNull(object.getClass().getAnnotation(annotationType));
    }

    public static boolean hasAnnotationByClass(Object object, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(object) || Objects.isNull(annotationType)) {
            return false;
        }

        if (hasDirectAnnotationByClass(object, annotationType)) {
            return true;
        }

        for (Annotation annotation : object.getClass().getAnnotations()) {
            if (hasAnnotationByClass(annotation, annotationType)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasAnnotationByClass(Annotation annotation, Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotation) || Objects.isNull(annotationType)) {
            return false;
        }

        Class<? extends Annotation> annotationClass = annotation.getClass();
        if (hasDirectAnnotationByClass(annotation, annotationType)) {
            return true;
        }

        for (Annotation subAnnotation : annotationClass.getAnnotations()) {
            if (hasAnnotationByClass(subAnnotation, annotationType)) {
                return true;
            }
        }

        return false;
    }
}
