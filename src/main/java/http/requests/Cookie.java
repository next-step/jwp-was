package http.requests;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Cookie {

    private static final String COOKIE_STRING_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> cookieShop;

    public Cookie(String valueOfHeader) {
        if (valueOfHeader == null) {
            this.cookieShop = Collections.emptyMap();
            return;
        }
        final String decodedHeader = URLDecoder.decode(valueOfHeader, StandardCharsets.UTF_8);
        this.cookieShop = parseHeader(decodedHeader);
    }

    private Map<String, String> parseHeader(String valueOfHeader) {
        return Arrays.stream(valueOfHeader.split(COOKIE_STRING_DELIMITER))
                .map(this::splitParameter)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitParameter(String raw) {
        final String[] keyAndValue = raw.split(KEY_VALUE_DELIMITER);
        return new AbstractMap.SimpleImmutableEntry<>(keyAndValue[0].trim(), keyAndValue[1].trim());
    }

    public String getValue(String key) {
        return cookieShop.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(cookieShop, cookie.cookieShop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookieShop);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookieShop=" + cookieShop +
                '}';
    }
}
