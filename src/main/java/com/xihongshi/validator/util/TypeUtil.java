package com.xihongshi.validator.util;

import java.math.BigDecimal;
import java.util.Objects;

public class TypeUtil {

    public static BigDecimal numberValue(Object object) {
        try {
            return new BigDecimal(String.valueOf(object));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long measureLength(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        return (long) String.valueOf(object).length();
    }
}
