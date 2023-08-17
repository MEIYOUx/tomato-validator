package com.xihongshi.validator.model;

import com.xihongshi.validator.exception.ValidateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 验证结果，其中包含了验证异常 {@link ValidateException} 的 List。
 * 验证不通过时应当实例化 {@link ValidateException} 异常的实例，并添加到验证结果中。
 * @author iuhay
 */
public class ValidateResult {

    /**
     * 保存验证异常 {@link ValidateException} 的 List
     */
    private final List<ValidateException> exceptionList = new ArrayList<>(0);

    /**
     * 添加验证异常
     * @param e 验证异常
     */
    public void addException(ValidateException e) {
        exceptionList.add(e);
    }

    /**
     * 当前验证结果是否通过，根据验证结果中的验证异常 List 的大小判断：
     * <pre>
     * {@code
     *     return exceptionList.isEmpty();
     * }
     * </pre>
     * @return true：通过验证；false：未通过验证。
     */
    public boolean isPassed() {
        return exceptionList.isEmpty();
    }

    /**
     * 获取验证异常 List
     * @return 不可修改的验证异常 List
     */
    public List<ValidateException> getExceptionList() {
        return Collections.unmodifiableList(exceptionList);
    }
}
