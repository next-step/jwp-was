package utils;

import java.util.Objects;

public class ValidUtils {

    private ValidUtils() {
    }

    public static void assertNotNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNotNull(Object object) {
        assertNotNull(object, "파라미터는 null일 수 없습니다");
    }

    public static void assertNotBlank(String value, String message) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
