package utils;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String nvl(String str, String defaultValue) {

        if(isEmpty(str)) {
            return defaultValue;
        }

        return str;
    }

    public static String nvl(String str) {
        return nvl(str, "");
    }
}
