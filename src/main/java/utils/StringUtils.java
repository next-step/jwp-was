package utils;

public class StringUtils {

    private StringUtils() {}

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String removeWhiteSpace(String value) {
        return value.replaceAll("\\s", "");
    }
}
