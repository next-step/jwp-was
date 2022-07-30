package webserver.http.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cookies {
    private final Map<String, Cookie> cookies;

    public Cookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies of(Cookie... cookies) {
        Map<String, Cookie> keyValues = Stream.of(cookies)
                .collect(Collectors.toMap(Cookie::getName, Function.identity()));

        return new Cookies(keyValues);
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }
}
