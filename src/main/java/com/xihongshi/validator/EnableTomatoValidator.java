package com.xihongshi.validator;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 表示启动验证，在启动类上标注该注解以开启验证功能。
 * <pre>
 * {@code
 *     @EnableTomatoValidator
 *     @SpringBootApplication
 *     public class Application {
 *         SpringApplication.run(Application.class, args);
 *     }
 * }
 * </pre>
 * @author iuhay
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TomatoValidatorAutoConfiguration.class)
public @interface EnableTomatoValidator {
}
