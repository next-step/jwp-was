package webserver.http.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RequestCookie {

    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> cookies;

    private RequestCookie(Map<String, String> cookies) {
        this.cookies = Collections.unmodifiableMap(cookies);
    }

    public static RequestCookie from(String stringCookie) {
        return new RequestCookie(toMap(stringCookie));
    }

    public static RequestCookie from(Map<String, String> cookies) {
        return new RequestCookie(cookies);
    }

    public static RequestCookie empty() {
        return new RequestCookie(Collections.emptyMap());
    }

    private static Map<String, String> toMap(String cookie) {
        if (cookie == null || cookie.isBlank()) {
            return Collections.emptyMap();
        }
        return Stream.of(cookie.split(COOKIE_DELIMITER))
                .map(RequestCookie::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] splitKeyValue = keyValue.split(KEY_VALUE_DELIMITER);
        if (splitKeyValue.length < 2) {
            return Map.entry(splitKeyValue[0], "");
        }
        return Map.entry(splitKeyValue[0].trim(), splitKeyValue[1].trim());
    }

    public Optional<String> value(String property) {
        if (!cookies.containsKey(property)) {
            return Optional.empty();
        }
        return Optional.ofNullable(cookies.get(property));
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestCookie cookie = (RequestCookie) o;
        return Objects.equals(cookies, cookie.cookies);
    }

    @Override
    public String toString() {
        return "RequestCookie{" +
                "cookies=" + cookies +
                '}';
    }
}
