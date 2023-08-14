package com.xihongshi.validator;

import com.xihongshi.model.UserReq;
import com.xihongshi.validator.core.Validator;
import com.xihongshi.validator.core.ValidatorFactory;
import com.xihongshi.validator.core.impl.DefaultValidatorFactory;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ValidatorFactoryTester {

    @Test
    void testCreateValidator() throws NoSuchFieldException, IllegalAccessException {

        UserReq userReq = new UserReq();
        userReq.setEmail("iuhay@gmail.com");
        Field field = userReq.getClass().getDeclaredField("email");

        ValidatorFactory factory = new DefaultValidatorFactory();
        for (Annotation annotation : field.getAnnotations()) {
            Validator validator = factory.createValidator(annotation.annotationType());
            field.setAccessible(true);
            boolean validate = validator.validate(field.get(userReq));
            System.out.println(validate);
        }
        System.out.println(factory);
    }
}
