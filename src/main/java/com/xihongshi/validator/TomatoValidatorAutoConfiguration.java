package com.xihongshi.validator;

import com.xihongshi.validator.core.ValidateAspect;
import com.xihongshi.validator.core.ValidatorFactory;
import com.xihongshi.validator.core.impl.DefaultValidatorFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证器的自动配置类，提供了默认的验证器工厂Bean、验证切面Bean的注入，
 * 并将验证器配置属性作用在这些Bean中。
 * @see DefaultValidatorFactory
 * @see ValidateAspect
 * @author iuhay
 */
@Configuration
@EnableConfigurationProperties(TomatoValidatorProperties.class)
public class TomatoValidatorAutoConfiguration {

    /**
     * 验证器配置属性
     */
    private final TomatoValidatorProperties properties;

    public TomatoValidatorAutoConfiguration(TomatoValidatorProperties properties) {
        this.properties = properties;
    }

    /**
     * 注入默认的验证器工厂
     * @return 默认的验证器工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public ValidatorFactory validatorFactory() {
        return new DefaultValidatorFactory();
    }

    /**
     * 注入验证切面
     * @param factory 验证器工厂
     * @return 验证切面
     */
    @Bean
    @ConditionalOnMissingBean
    public ValidateAspect validateAspect(ValidatorFactory factory) {
        return new ValidateAspect(properties, factory);
    }
}
