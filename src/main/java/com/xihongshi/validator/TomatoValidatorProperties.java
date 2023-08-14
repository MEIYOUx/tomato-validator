package com.xihongshi.validator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties(prefix = "tomato.validator")
public class TomatoValidatorProperties {

    private Boolean enable = Boolean.TRUE;
    private Boolean fastValidate = Boolean.TRUE;
    private Integer multiFailCode = 400;
    private String multiFailMessage = "参数验证失败";

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = Objects.isNull(enable) ? Boolean.TRUE : enable;
    }

    public Boolean isFastValidate() {
        return fastValidate;
    }

    public void setFastValidate(Boolean fastValidate) {
        this.fastValidate = Objects.isNull(fastValidate) ? Boolean.TRUE : fastValidate;
    }

    public Integer getMultiFailCode() {
        return multiFailCode;
    }

    public void setMultiFailCode(Integer multiFailCode) {
        this.multiFailCode = multiFailCode;
    }

    public String getMultiFailMessage() {
        return multiFailMessage;
    }

    public void setMultiFailMessage(String multiFailMessage) {
        this.multiFailMessage = multiFailMessage;
    }
}