package utils;

import java.util.Objects;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }
}
