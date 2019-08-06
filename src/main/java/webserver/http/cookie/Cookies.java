package webserver.http.cookie;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class Cookies {

    private static final Cookies EMPTY = new Cookies(Collections.emptyMap());

    private static final String SEPARATOR = "; ";

    private final Map<String, String> cookie;

    private Cookies(final Map<String, String> cookies) {
        this.cookie = cookies;
    }

    public static Cookies of(final String rawCookies) {
        if (StringUtils.isBlank(rawCookies)) {
            return EMPTY;
        }

        return Arrays.stream(rawCookies.split(SEPARATOR))
                .map(Cookie::of)
                .collect(collectingAndThen(toMap(Cookie::getKey, Cookie::getValue), Cookies::new));
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
