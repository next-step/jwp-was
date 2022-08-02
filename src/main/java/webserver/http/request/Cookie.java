package webserver.http.request;

import java.util.*;
import java.util.stream.Collectors;

public class Cookie {
    public static final String REQUEST_COOKIE_HEADER = "Cookie";
    public static final String RESPONSE_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PATH_KEY = "Path";

    private final Map<String, String> valuesByKey = new HashMap<>();

    private Cookie() {
    }

    public static Cookie from(String cookiesString) {
        Cookie cookie = new Cookie();
        if (Objects.isNull(cookiesString) || cookiesString.isEmpty()) {
            return cookie;
        }

        Arrays.stream(cookiesString.split(COOKIE_DELIMITER))
                .map(cookieString -> cookieString.split(KEY_VALUE_DELIMITER))
                .forEach(keyValue -> cookie.set(keyValue[0], keyValue[1]));
        return cookie;
    }

    public String get(String key) {
        return valuesByKey.get(key);
    }

    public void set(String key, String value) {
        valuesByKey.put(key, value);
    }

    public String getResponseCookie() {
        List<String> cookieStrings = valuesByKey.entrySet().stream()
                .filter(entry -> !PATH_KEY.equals(entry.getKey()))
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.toList());
        return String.join(COOKIE_DELIMITER, cookieStrings) + COOKIE_DELIMITER + PATH_KEY + KEY_VALUE_DELIMITER + "/";
    }
}
