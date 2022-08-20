package utils;

import exception.InvalidCookieException;

import java.util.HashMap;
import java.util.Map;

import static exception.CookieExceptionMessage.INVALID_COOKIE_VALUE;
import static utils.DelimiterConstants.COOKIE_VALUE_DELIMITER;
import static utils.DelimiterConstants.PARAMETER_KEY_VALUE_DELIMITER;

public class CookieUtils {
    private static final int COOKIE_VALUE_LANGTH = 2;

    public static Map<String, Object> createCookieMap(String cookies) {
        Map<String, Object> map = new HashMap<>();
        String[] split = cookies.split(COOKIE_VALUE_DELIMITER);
        for (String cookie : split) {
            String[] splitCookie = split(cookie);
            map.put(splitCookie[0], splitCookie[1]);
        }
        return map;
    }

    private static String[] split(String cookie) {
        String[] splitCookie = cookie.split(PARAMETER_KEY_VALUE_DELIMITER);
        if (splitCookie.length != COOKIE_VALUE_LANGTH) {
            throw new InvalidCookieException(INVALID_COOKIE_VALUE);
        }
        return splitCookie;
    }
}
