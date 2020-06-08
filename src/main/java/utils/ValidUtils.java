package utils;

import java.util.Objects;

public class ValidUtils {

    private ValidUtils() {
    }

    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "파라미터는 null일 수 없습니다");
    }

    public static void notBlank(String value, String message) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
