package com.xihongshi.validator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties(prefix = "tomato.validator")
public class TomatoValidatorProperties {

    private Boolean enable = Boolean.TRUE;

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = Objects.isNull(enable) ? Boolean.TRUE : enable;
    }
}