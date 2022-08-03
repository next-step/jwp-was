package webserver.http.domain;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class Cookies {
    private static final String EACH_COOKIE_DELIMITER_REGEX = "; ";

    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";

    private final Map<String, Cookie> cookies;

    public Cookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public boolean existsCookie(String name, String value) {
        if (cookies.containsKey(name)) {
            Cookie cookie = cookies.get(name);
            return cookie.equalsValue(value);
        }
        return false;
    }

    public Optional<Cookie> getCookie(String name) {
        return Optional.ofNullable(cookies.get(name));
    }

    public static Cookies from(String message) {
        if (Objects.isNull(message) || message.isBlank()) {
            return new Cookies(new LinkedHashMap<>());
        }

        return Arrays.stream(message.split(EACH_COOKIE_DELIMITER_REGEX))
                .map(cookie -> KeyValuePair.from(cookie, COOKIE_KEY_VALUE_DELIMITER, false))
                .collect(collectingAndThen(
                                toMap(
                                        KeyValuePair::getKey, pair ->
                                                new Cookie(pair.getKey(), pair.getValue()),
                                        (oldCookie, newCookie) -> newCookie,
                                        LinkedHashMap::new
                                ),
                                Cookies::new
                        )
                );
    }
}
