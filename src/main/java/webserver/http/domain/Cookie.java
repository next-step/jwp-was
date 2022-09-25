package webserver.http.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cookie {

    public static final String COOKIE_SPLIT_SYMNOL = "; ";
    public static final String COOKIE_KEY_VALUE_SPLIT_SYMBOL = "=";
    private final String name;
    private final String value;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Map<String, Cookie> createCookie(RequestHeader header) {
        String cookie = header.getValue("Cookie");

        if (cookie == null || cookie.isEmpty()) {
            return new HashMap<>();
        }
        return Arrays.stream(cookie.split(COOKIE_SPLIT_SYMNOL))
                .map(item -> {
                    String[] items = item.split(COOKIE_KEY_VALUE_SPLIT_SYMBOL);
                    return new Cookie(items[0], items[1]);
                })
                .collect(Collectors.toMap(Cookie::name, Function.identity(), (x, y) -> x));
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    public String path() {
        return path;
    }

//    public SessionId sessionId(String key) {
//        if (!cookies.containsKey(key)) {
//            return null;
//        }
//        return sessionId(cookies.get(key));
//    }
}
