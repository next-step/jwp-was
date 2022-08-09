package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_HEADER_KEY = "Cookie";
    private static final String HEADER_DELIMITER = ";";

    private final Map<String, Cookie> cookies;

    public Cookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public Cookies(HttpHeader headers) {
        String cookieHeader = headers.getHeader(COOKIE_HEADER_KEY);

        if (cookieHeader == null) {
            cookies = Collections.emptyMap();
            return;
        }

        cookies = Arrays.stream(cookieHeader.replaceAll(" ", "").split(HEADER_DELIMITER))
                .map(Cookie::new)
                .collect(Collectors.toMap(Cookie::getKey, cookie -> cookie));
    }

    public Cookie getCookie(String key) {
        return cookies.get(key);
    }
}
