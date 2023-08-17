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
import java.util.Objects;

@Aspect
public class ValidateAspect {

    private final TomatoValidatorProperties properties;
    private final ValidatorFactory validatorFactory;

    public ValidateAspect(TomatoValidatorProperties properties, ValidatorFactory validatorFactory) {
        this.properties = properties;
        this.validatorFactory = validatorFactory;
    }

    @Before("@annotation(com.xihongshi.validator.constraints.Valid)")
    public void validate(JoinPoint joinPoint) {
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
                Integer code = getCodeFromAnnotation(item.getAnnotation());
                String message = getMessageFromAnnotation(item.getAnnotation());
                result.addException(new ValidateException(code, message));
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

    private ValidateContext createContext(Method method, Object[] args) {
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

    private List<ValidateItem> createItems(Parameter parameter, Object arg) {
        List<ValidateItem> items = new ArrayList<>();
        Class<?> type = parameter.getType();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            items.addAll(createItems(field, arg));
        }
        return items;
    }

    private List<ValidateItem> createItems(Field field, Object arg) {
        List<ValidateItem> items = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();
        field.setAccessible(true);
        for (Annotation annotation : annotations) {
            if (AnnotationUtil.hasAnnotation(annotation, Constraints.class)) {
                items.add(new ValidateItem(
                        safetyGet(field, arg), 
                        annotation
                ));
            }
        }
        return items;
    }

    /**
     * 安全地从 Field 对象中获取值，保证至少能够获取到 null值，而不是抛出异常。
     * @param field  字段
     * @param object 对象
     * @return Field 对象中获取值，保证至少能够获取到 null值，而不是抛出异常。
     */
    private Object safetyGet(Field field, Object object) {
        if (Objects.isNull(field)) {
            return null;
        }
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 从注解中解析出 code，通过获取注解中定义的名称为 {@link Constraints}.CODE 的方法。
     * 如果解析失败，则返回 properties 中配置的默认错误代码 {@link TomatoValidatorProperties}.defaultErrorCode。
     * @param annotation 注解对象
     * @return 注解中的code，如果解析失败，则返回 properties 中配置的默认错误代码 {@link TomatoValidatorProperties}.defaultErrorCode。
     * @see Constraints
     * @see TomatoValidatorProperties
     */
    private int getCodeFromAnnotation(Annotation annotation) {
        if (Objects.isNull(annotation)) {
            return properties.getDefaultErrorCode();
        }
        
        Object code;
        try {
            code = annotation.annotationType().getDeclaredMethod(Constraints.CODE).invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return properties.getDefaultErrorCode();
        }
        
        try {
            return Integer.parseInt(String.valueOf(code));
        }
        catch (NumberFormatException e) {
            return properties.getDefaultErrorCode();
        }
    }
    
    /**
     * 从注解中解析出 message，通过获取注解中定义的名称为 {@link Constraints}.MESSAGE 的方法。
     * 如果解析失败，则返回 properties 中配置的默认错误消息 {@link TomatoValidatorProperties}.defaultErrorMessage。
     * @param annotation 注解对象
     * @return 注解中的message，如果解析失败，则返回 properties 中配置的默认错误消息 {@link TomatoValidatorProperties}.defaultErrorMessage。
     * @see Constraints
     * @see TomatoValidatorProperties
     */
    private String getMessageFromAnnotation(Annotation annotation) {
        if (Objects.isNull(annotation)) {
            return properties.getDefaultErrorMessage();
        }
        
        Object message;
        try {
            message = annotation.annotationType().getDeclaredMethod(Constraints.MESSAGE).invoke(annotation);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return properties.getDefaultErrorMessage();
        }
        
        if (!(message instanceof String)) {
            return properties.getDefaultErrorMessage();
        }
        return (String) message;
    }
}
