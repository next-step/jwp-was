package utils;

public final class StringUtils {

    private StringUtils() { }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    public static boolean isBlank(final String string) {
        return string == null || string.isBlank();
    }
}
