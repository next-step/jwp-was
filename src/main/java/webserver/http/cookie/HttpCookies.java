package webserver.http.cookie;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class HttpCookies implements Cookies {

    private static final Cookies EMPTY = new HttpCookies(Collections.emptyMap());

    private static final String SEPARATOR = "; ";

    private final Map<String, String> cookie;

    private HttpCookies(final Map<String, String> cookies) {
        this.cookie = cookies;
    }

    public static Cookies of(final String rawCookies) {
        if (StringUtils.isBlank(rawCookies)) {
            return EMPTY;
        }

        return Arrays.stream(rawCookies.split(SEPARATOR))
                .map(Cookie::of)
                .collect(collectingAndThen(toMap(Cookie::getKey, Cookie::getValue), HttpCookies::new));
    }

    public Optional<String> getString(final String key) {
        return Optional.ofNullable(cookie.get(key));
    }

    public boolean getBoolean(final String key) {
        return getString(key).map(Boolean::valueOf)
                .orElse(false);
    }

    public boolean isEmpty() {
        return cookie.isEmpty();
    }
}
