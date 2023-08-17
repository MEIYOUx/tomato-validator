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

/**
 * 验证切面，通过AOP的方式使验证器作用与标记了 {@link Valid} 注解的方法。
 * 在验证切面的验证方法中，主要做了如下工作：
 *     1、获取标记了 {@link Valid} 注解的方法 和 参数列表。
 *     2、根据方法 和 参数列表创建验证上下文。
 *     3、通过验证器工厂创建每个验证项的验证器，并进行验证。
 *     4、处理验证结果。
 * @author iuhay
 */
@Aspect
public class ValidateAspect {

    private final TomatoValidatorProperties properties;
    private final ValidatorFactory validatorFactory;

    public ValidateAspect(TomatoValidatorProperties properties, ValidatorFactory validatorFactory) {
        this.properties = properties;
        this.validatorFactory = validatorFactory;
    }

    /**
     * 验证切面执行的验证方法，主要做了如下工作：
     *     <p>1、获取标记了 {@link Valid} 注解的方法参数列表。</p>
     *     <p>2、根据参数列表创建验证上下文。</p>
     *     <p>3、通过验证器工厂创建每个验证项的验证器，并进行验证。</p>
     *     <p>4、处理验证结果。</p>
     * @param joinPoint 切入点
     * @throws ValidateException 当开启快速验证 并且 验证失败时，则抛出该类型异常。
     * @throws MultiValidateException 当关闭快速验证 并且 验证失败时，则抛出该类型异常。
     */
    @Before("@annotation(com.xihongshi.validator.constraints.Valid)")
    public void validate(JoinPoint joinPoint) {
        if (!properties.isEnable()) {
            return;
        }
        //  1、获取参数列表
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //  2、创建验证上下文
        ValidateContext context = createContext(method, joinPoint.getArgs());
        //  3、通过验证器工厂创建每个验证项的验证器，并进行验证。
        ValidateResult result = processingContext(context);
        //  4、处理验证结果
        processingResult(result);
    }

    /**
     * 根据方法 和 参数列表创建验证上下文，该方法不会返回 null。
     * @param method 方法
     * @param args   方法的参数列表
     * @return 验证上下文，保证不会返回 null。
     */
    private ValidateContext createContext(Method method, Object[] args) {
        ValidateContext context = new ValidateContext();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            context.addItems(createItems(parameters[i], args[i]));
        }
        return context;
    }

    /**
     * 根据参数创建验证项 List，该方法至少返回空的 List。
     * @param parameter 参数
     * @param arg       传入的参数值对象
     * @return 验证项 List，保证不会返回 null，至少返回空的 List。
     */
    private List<ValidateItem> createItems(Parameter parameter, Object arg) {
        List<ValidateItem> items = new ArrayList<>();
        Class<?> type = parameter.getType();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            //  只处理标记了 @Valid 注解的参数
            if (AnnotationUtil.hasDirectAnnotation(parameter, Valid.class)) {
                items.addAll(createItems(field, arg));
            }
        }
        return items;
    }

    /**
     * 根据字段创建验证项 List，该方法至少返回空的 List。
     * 因为每个字段上可能被多个约束注解标记，每个约束注解都需要创建一个对应的验证项，
     * 所以对于一个字段应该创建一个验证项 List。
     * @param field 字段
     * @param arg   传入的参数值对象
     * @return 验证项 List，保证不会返回 null，至少返回空的 List。
     */
    private List<ValidateItem> createItems(Field field, Object arg) {
        List<ValidateItem> items = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();
        field.setAccessible(true);
        for (Annotation annotation : annotations) {
            //  只处理标记了约束注解的字段
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
     * 处理验证上下文，遍历验证上下文中的验证项 List，创建对应的验证器 并 进行验证。
     * 如果开启了快速验证，当验证失败时则不进行后续的验证。
     * 如果关闭了快速验证，当验证失败时则会进行后续的验证。
     * @param context 需要处理的验证上下文
     * @return 验证结果，保证不返回 null。
     */
    private ValidateResult processingContext(ValidateContext context) {
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
        return result;
    }

    /**
     * 处理验证结果，如果验证通过则不受影响。
     * 否则，如果开启了快速验证，验证结果中只存在一条验证异常。
     *      如果关闭了快速验证，验证结果中可能存在多条验证异常。
     * @param result 需要处理的验证结果
     * @throws ValidateException 开启了快速验证，当验证不通过时则抛出该类型异常。
     * @throws MultiValidateException 关闭了快速验证，当验证不通过时则抛出该类型异常。
     */
    private void processingResult(ValidateResult result) {
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
