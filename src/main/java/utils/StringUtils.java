package utils;

public class StringUtils {

    public static boolean isEmpty(String val) {
        return val == null || val.trim().isEmpty();
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }
}
