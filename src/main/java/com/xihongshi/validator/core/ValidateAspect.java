package com.xihongshi.validator.core;

import com.xihongshi.validator.TomatoValidatorProperties;
import com.xihongshi.validator.constraints.Constraints;
import com.xihongshi.validator.constraints.Valid;
import com.xihongshi.validator.exception.MultiValidateException;
import com.xihongshi.validator.exception.ValidateException;
import com.xihongshi.validator.model.ValidateItem;
import com.xihongshi.validator.model.ValidateResult;
import com.xihongshi.validator.util.AnnotationUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

@Aspect
public class ValidateAspect {

    private final TomatoValidatorProperties properties;
    private final ValidatorFactory validatorFactory;

    public ValidateAspect(TomatoValidatorProperties properties, ValidatorFactory validatorFactory) {
        this.properties = properties;
        this.validatorFactory = validatorFactory;
    }

    @Before("@annotation(com.xihongshi.validator.constraints.Valid)")
    public void validate(JoinPoint joinPoint) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (!properties.isEnable()) {
            return;
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ValidateContext context = createContext(method, joinPoint.getArgs());

        ValidateResult result = new ValidateResult();
        for (ValidateItem item : context.getItems()) {
            Validator validator = validatorFactory.createValidator(item.getAnnotation().annotationType());
            boolean isPassed;
            try {
                isPassed = validator.validate(item.getObject(), item.getAnnotation());
            }
            catch (ValidateException e) {
                result.addException(e);
                continue;
            }
            if (!isPassed) {
                result.addException(new ValidateException(item.getCode(), item.getMessage()));
            }
            if (!isPassed && properties.isFastValidate()) {
                break;
            }
        }

        if (result.isPassed()) {
            return;
        }
        if (properties.isFastValidate()) {
            throw result.getExceptionList().get(0);
        }
        throw new MultiValidateException(
                properties.getDefaultErrorCode(),
                properties.getDefaultErrorMessage(),
                result.getExceptionList()
        );
    }

    private ValidateContext createContext(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ValidateContext context = new ValidateContext();
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

    private List<ValidateItem> createItems(Parameter parameter, Object arg) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<ValidateItem> items = new ArrayList<>();
        Class<?> type = parameter.getType();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            items.addAll(createItems(field, arg));
        }
        return items;
    }

    private List<ValidateItem> createItems(Field field, Object arg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ValidateItem> items = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (AnnotationUtil.hasAnnotation(annotation, Constraints.class)) {
                Object code = annotation.annotationType().getDeclaredMethod(Constraints.CODE).invoke(annotation);
                Object message = annotation.annotationType().getDeclaredMethod(Constraints.MESSAGE).invoke(annotation);
                items.add(new ValidateItem(field.get(arg), annotation, (Integer) code, (String) message));
            }
        }
        return items;
    }
}
