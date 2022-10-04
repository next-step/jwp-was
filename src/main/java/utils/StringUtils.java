package utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isNullAndBlank(String input) {
        return input == null || input.length() == 0;
    }
}
