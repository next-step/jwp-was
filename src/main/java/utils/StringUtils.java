package utils;

import java.util.Objects;

public class StringUtils {
    public static boolean isEmpty(String target) {
        return Objects.isNull(target) || target.length() <= 0;
    }

    public static boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }
}
