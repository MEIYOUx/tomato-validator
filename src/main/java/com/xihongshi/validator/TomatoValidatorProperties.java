package com.xihongshi.validator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * 验证器配置属性，包含了基础的配置属性并为某些属性提供了默认值。
 * 这些配置属性将在验证切面中产生作用
 * @see com.xihongshi.validator.core.ValidateAspect
 * @author iuhay
 */
@ConfigurationProperties(prefix = "tomato.validator")
public class TomatoValidatorProperties {

    /**
     * 是否开启验证，true：开启；false：关闭，默认 true。
     */
    private Boolean enable = Boolean.TRUE;

    /**
     * 是否开启快速验证：true：开启；false：关闭，默认 true。
     * 开启快速验证后，当第一个验证失败时抛出验证异常，不会进行后续校验。
     * 关闭快速验证后，会完成所有的验证，并抛出所有验证异常。
     * @see com.xihongshi.validator.exception.ValidateException
     * @see com.xihongshi.validator.exception.MultiValidateException
     */
    private Boolean fastValidate = Boolean.TRUE;

    /**
     * 默认错误代码，默认值 400。
     * 在关闭快速验证后，抛出的异常中的 code 会采用该默认错误代码。
     * @see com.xihongshi.validator.exception.MultiValidateException
     */
    private Integer defaultErrorCode = 400;

    /**
     * 默认错误消息，默认值 "参数验证失败"。
     * 在关闭快速验证后，抛出的异常中的 message 会采用该默认错误消息。
     * @see com.xihongshi.validator.exception.MultiValidateException
     */
    private String defaultErrorMessage = "参数验证失败";

    /**
     * 是否开启验证
     * @return true：开启；false：关闭
     */
    public Boolean isEnable() {
        return enable;
    }

    /**
     * 设置开启/关闭验证，当传入 null 时，将采用默认值 true
     * @param enable true：开启；false：关闭
     */
    public void setEnable(Boolean enable) {
        this.enable = Objects.isNull(enable) ? Boolean.TRUE : enable;
    }

    /**
     * 是否开启快速验证
     * @return true：开启；false：关闭
     */
    public Boolean isFastValidate() {
        return fastValidate;
    }

    /**
     * 设置开启/关闭快速验证，当传入 null 时，将采用默认值 true
     * @param fastValidate true：开启；false：关闭
     */
    public void setFastValidate(Boolean fastValidate) {
        this.fastValidate = Objects.isNull(fastValidate) ? Boolean.TRUE : fastValidate;
    }

    /**
     * 获取默认的错误代码
     * @return 默认的错误代码
     */
    public Integer getDefaultErrorCode() {
        return defaultErrorCode;
    }

    /**
     * 设置默认的错误代码，当传入 null 时，将采用默认值 400
     * @param defaultErrorCode 默认的错误代码
     */
    public void setDefaultErrorCode(Integer defaultErrorCode) {
        this.defaultErrorCode = Objects.isNull(defaultErrorCode) ? 400 : defaultErrorCode;
    }

    /**
     * 获取默认的错误消息
     * @return 默认的错误消息
     */
    public String getDefaultErrorMessage() {
        return defaultErrorMessage;
    }

    /**
     * 设置默认的错误消息，当传入 null 时，将采用默认值 "参数验证失败"
     * @param defaultErrorMessage 默认的错误消息
     */
    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = Objects.isNull(defaultErrorMessage) ? "参数验证失败" : defaultErrorMessage;
    }
}