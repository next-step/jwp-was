package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StringUtils {

    public static String convertToNullIfEmpty(String text) {
        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static String[] splitIntoPair(String target, String delimiter) {
        if (isEmpty(target) || isEmpty(delimiter)) {
            throw new IllegalArgumentException();
        }

        int indexOfDelimiter = target.indexOf(delimiter);

        String first = target;
        String second = "";

        if (indexOfDelimiter > 0) {
            first = target.substring(0, indexOfDelimiter);
            second = target.substring(indexOfDelimiter + 1);
        }

        return new String[]{first, second};
    }

    public static String decode(String target) {
        try {
            if (target == null) {
                return null;
            }
            return URLDecoder.decode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is not supported.");
        }
    }
}
