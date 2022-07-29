package webserver.http.cookie;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cookies {
    private final Map<String, Cookie> cookies;

    public Cookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies of(Cookie... cookies) {
        Map<String, Cookie> keyValues = Set.of(cookies).stream()
                .collect(Collectors.toMap(Cookie::getName, Function.identity()));

        return new Cookies(keyValues);
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }
}
