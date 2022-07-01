package webserver.domain.http;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cookies {

    private final static String COOKIE_DELIMITER = "; ";
    private final static String KEY_VALUE_DELIMITER = "=";

    private final List<Cookie> cookies;

    public Cookies() {
        this.cookies = List.of();
    }

    private Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies from(String input) {
        List<Cookie> cookies = Arrays.stream(input.split(COOKIE_DELIMITER))
                .map(it -> {
                    String[] keyValue = it.split(KEY_VALUE_DELIMITER);
                    return new Cookie(keyValue[0], keyValue[1]);
                })
                .collect(Collectors.toUnmodifiableList());

        return new Cookies(cookies);
    }

    public Cookie get(String key) {
        return this.cookies.stream()
                .filter(it -> it.getName().equals(key))
                .findFirst()
                .orElse(null);
    }

    public HttpSession getSession() {
        Cookie cookie = get(HttpSession.SESSION_ID);
        if (Objects.isNull(cookie)) {
            return HttpSessions.create();
        }
        return HttpSessions.get(cookie.getValue());
    }
}
