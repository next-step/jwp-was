package webserver.common;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import webserver.common.exception.IllegalCookieException;
import webserver.common.exception.IllegalCookieKeyException;

public class HttpCookie {
    private final Map<String, String> cookies;

    public HttpCookie() {
        this.cookies = new LinkedHashMap<>();
    }

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String get(String key) {
        return Optional.ofNullable(cookies.get(key))
                .orElseThrow(() -> new IllegalCookieKeyException(key));
    }

    public HttpCookie put(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public static HttpCookie from(String cookieString) {
        if (!cookieString.contains("=")) {
            return new HttpCookie();
        }
        String[] queries = cookieString.split("&");
        try {
            return new HttpCookie(parse(queries));
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new IllegalCookieException(cookieString);
        }
    }

    private static Map<String, String> parse(String[] cookies) {
        String delimiter = "=";
        return Arrays.stream(cookies)
                .map(cookie -> cookie.split(delimiter))
                .collect(Collectors.toMap(
                        tuple -> tuple[0],
                        tuple -> tuple[1]
                ));
    }

    @Override
    public String toString() {
        return cookies.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&"));
    }
}
