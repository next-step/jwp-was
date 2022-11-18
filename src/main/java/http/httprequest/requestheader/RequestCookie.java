package http.httprequest.requestheader;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestCookie {
    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> cookies;

    RequestCookie(Map<String, String> cookies) {
        if (CollectionUtils.isEmpty(cookies)) {
            cookies = Collections.emptyMap();
        }

        this.cookies = Collections.unmodifiableMap(cookies);
    }

    public static RequestCookie from (String cookies) {
        return new RequestCookie(parseCookieString(cookies));
    }

    private static Map<String, String> parseCookieString(String cookies) {
        if (StringUtils.isEmpty(cookies)) {
            return Collections.emptyMap();
        }

        return Stream.of(cookies.split(COOKIE_DELIMITER))
                .map(cookie -> cookie.split(KEY_VALUE_DELIMITER))
                .map(RequestCookie::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> parseToEntry(String[] keyValuePair) {
        if (keyValuePair.length < 2) {
            return Map.entry(keyValuePair[KEY_INDEX], "");
        }

        return Map.entry(keyValuePair[KEY_INDEX].trim(), keyValuePair[VALUE_INDEX].trim());
    }

    public Optional<String> getValue(String key) {
        if (!cookies.containsKey(key)) {
            return Optional.empty();
        }

        return Optional.of(cookies.get(key));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCookie requestCookie = (RequestCookie) o;
        return cookies.equals(requestCookie.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }
}
