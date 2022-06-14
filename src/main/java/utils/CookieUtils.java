package utils;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieUtils {

    private static final String SEMI_COLON = ";";
    private static final String EQUAL = "=";

    /**
     * Cookie: logined=true; Path=/
     *
     * @param cookieMapStr
     */
    public static Map<String, String> strToCookieMap(String cookieMapStr) {
        if (Strings.isBlank(cookieMapStr)) {
            return new HashMap<>();
        }

        return Arrays.stream(cookieMapStr.trim().split(SEMI_COLON))
                .filter(str -> str.contains(EQUAL))
                .map(str -> str.split(EQUAL))
                .filter(kv -> kv.length > 1)
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    }

    public static String cookieMapToStr(Map<String, String> cookieMap) {
        return cookieMap.entrySet().stream()
                .map(e -> e.getKey() + EQUAL + e.getValue())
                .collect(Collectors.joining(SEMI_COLON));
    }
}
