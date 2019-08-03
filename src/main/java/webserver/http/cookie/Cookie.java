package webserver.http.cookie;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class Cookie {

    private static final Cookie EMPTY = new Cookie(Collections.emptyMap());

    private static final String SEPARATOR = "; ";

    private final Map<String, String> cookie;

    private Cookie(final Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public static Cookie of(final String rawCookie) {
        if (StringUtils.isBlank(rawCookie)) {
            return EMPTY;
        }

        return Arrays.stream(rawCookie.split(SEPARATOR))
                .map(CookieValue::of)
                .collect(collectingAndThen(toMap(CookieValue::getKey, CookieValue::getValue), Cookie::new));
    }

    public String getString(final String key) {
        return cookie.get(key);
    }

    public boolean getBoolean(final String key) {
        return Boolean.valueOf(getString(key));
    }

    public boolean isEmpty() {
        return cookie.isEmpty();
    }
}
