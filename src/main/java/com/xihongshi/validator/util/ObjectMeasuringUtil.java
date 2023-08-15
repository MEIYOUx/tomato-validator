package com.xihongshi.validator.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 对象测量工具类，包含了针对Object对象的测量方法。
 * @author iuhay
 */
public class ObjectMeasuringUtil {

    /**
     * 测量对象所对应的数值，实现方式如下：
     * <pre>
     * {@code
     *     return new BigDecimal(String.valueOf(object));
     * }
     * </pre>
     * 当对象无法解析为数值类型时，方法将返回 null。
     * @param object 待测量对象
     * @return 对象所对应的数值；null：对象无法解析为数值类型。
     */
    public static BigDecimal numberValue(Object object) {
        try {
            return new BigDecimal(String.valueOf(object));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 测量对象的长度，实现方式如下：
     * <pre>
     * {@code
     *     return (long) String.valueOf(object).length();
     * }
     * </pre>
     * 当对象为 null 时，方法将返回 null。
     * @param object 待测量对象
     * @return 对象对应字符串的长度；null：对象为 null。
     */
    public static Long measureLength(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        return (long) String.valueOf(object).length();
    }
}
