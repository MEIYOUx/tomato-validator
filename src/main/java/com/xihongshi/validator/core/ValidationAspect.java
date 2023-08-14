package com.xihongshi.validator.core;

import com.xihongshi.validator.constraints.Constraints;
import com.xihongshi.validator.constraints.Valid;
import com.xihongshi.validator.exception.ValidationException;
import com.xihongshi.validator.model.ValidationItem;
import com.xihongshi.validator.util.AnnotationUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Aspect
public class ValidationAspect {

    private final ValidatorFactory validatorFactory;

    public ValidationAspect(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Before("@annotation(com.xihongshi.validator.constraints.Constraints)")
    public void validate(JoinPoint joinPoint) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ValidationContext context = createContext(method, joinPoint.getArgs());
        for (ValidationItem item : context.getItems()) {
            Validator validator = validatorFactory.createValidator(item.getAnnotationType());
            if (Objects.nonNull(validator) && !validator.validate(item.getObject())) {
                throw new ValidationException(item.getCode(), item.getMessage());
            }
        }
    }

    private ValidationContext createContext(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ValidationContext context = new ValidationContext();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (!AnnotationUtil.hasDirectAnnotation(parameter, Valid.class)) {
                continue;
            }
            context.addItems(createItems(parameter, args[i]));
        }
        return context;
    }

    private List<ValidationItem> createItems(Parameter parameter, Object arg) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<ValidationItem> items = new ArrayList<>();
        Class<?> type = parameter.getType();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            items.addAll(createItems(field, arg));
        }
        return items;
    }

    private List<ValidationItem> createItems(Field field, Object arg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ValidationItem> items = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (AnnotationUtil.hasAnnotationByClass(annotation, Constraints.class)) {
                Object code = annotation.annotationType().getDeclaredMethod(Constraints.CODE).invoke(annotation);
                Object message = annotation.annotationType().getDeclaredMethod(Constraints.MESSAGE).invoke(annotation);
                items.add(new ValidationItem(field.get(arg), annotation.annotationType(), (Integer) code, (String) message));
            }
        }
        return items;
    }
}
