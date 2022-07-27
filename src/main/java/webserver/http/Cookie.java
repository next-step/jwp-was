package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private static final String DELIMITER_FOR_COOKIES = ";";
    private static final String DELIMITER_FOR_COOKIE = "=";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;

    private final Map<String, String> cookies;

    public Cookie(String cookies) {
        this.cookies = parseCookies(cookies);
    }

    private Map<String, String> parseCookies(String cookieString) {
        if (cookieString == null || cookieString.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, String> cookies = new HashMap<>();
        String[] tokens = cookieString.split(DELIMITER_FOR_COOKIES);
        Arrays.stream(tokens).forEach(token -> {
            String[] nameValuePair = token.trim().split(DELIMITER_FOR_COOKIE);
            cookies.put(nameValuePair[INDEX_ZERO], nameValuePair[INDEX_ONE]);
        });

        return cookies;
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }
}
