package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class HttpStringUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpStringUtils.class);

    public static String[] split(String input, String delimiter) {
        return input.split(delimiter);
    }

    public static String substring(String input, String delimiter, int index) {
        String[] values = split(input, delimiter);
        return values[index];
    }

    public static boolean isPatternMatch(String regex, String input) {
        return Pattern.matches(regex, input);
    }

    public static boolean checkLoginCookie(String cookie) {
        String isLogined = extractValue(cookie, "logined");
        return "true".equals(isLogined);
    }

    public static String extractSessionId(String cookie) {
        return extractValue(cookie, HttpStringType.SESSION_ID.getType());
    }

    private static String extractValue(String parameters, String key) {
        if (parameters == null) {
            return null;
        }

        String[] dataSets = split(parameters, HttpStringType.DLM_SEMICOLON.getType());

        for (String dataSet : dataSets) {
            log.debug("data : {}", dataSet);
            if (dataSet.trim().startsWith(key)) {
                return substring(dataSet.trim(), HttpStringType.DLM_EQUAL.getType(), 1);
            }
        }

        return null;
    }

    public static String addValue(String values, String newValue) {
        if (values == null) {
            return newValue;
        }

        return concat(values, HttpStringType.DLM_SEMICOLON.getType(), HttpStringType.DLM_SPACE.getType(), newValue);
    }

    public static String concat(String... items) {
        StringBuilder sb = new StringBuilder();

        for (String item : items) {
            sb.append(item);
        }

        return sb.toString();
    }
}
