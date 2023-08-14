package com.xihongshi.validator;

import com.xihongshi.validator.core.ValidationAspect;
import com.xihongshi.validator.core.ValidatorFactory;
import com.xihongshi.validator.core.impl.DefaultValidatorFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TomatoValidatorProperties.class)
public class TomatoValidatorAutoConfiguration {

    private final TomatoValidatorProperties properties;

    public TomatoValidatorAutoConfiguration(TomatoValidatorProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidatorFactory validatorFactory() {
        return new DefaultValidatorFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidationAspect validationAspect(ValidatorFactory factory) {
        return new ValidationAspect(factory);
    }
}
