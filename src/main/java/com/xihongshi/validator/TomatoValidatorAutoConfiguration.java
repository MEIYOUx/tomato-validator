package com.xihongshi.validator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TomatoValidatorProperties.class)
public class TomatoValidatorAutoConfiguration {

    private final TomatoValidatorProperties properties;

    public TomatoValidatorAutoConfiguration(TomatoValidatorProperties properties) {
        this.properties = properties;
    }


}
