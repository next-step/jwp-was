package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {

    public static final Cookies EMPTY = new Cookies(Collections.emptyMap());
    public static final String PATH = "Path";

    private static final String SEPARATOR = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";

    private Map<String, String> cookies;

    private Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookies newInstance() {
        return new Cookies(new LinkedHashMap<>());
    }

    public static Cookies parse(String rawCookies) {
        Map<String, String> cookies = Arrays.stream(rawCookies.split(SEPARATOR))
                .map(cookie -> StringPair.split(cookie, KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(
                        StringPair::getKey,
                        StringPair::getValue,
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        },
                        LinkedHashMap::new
                ));
        return new Cookies(cookies);
    }

    public void add(String name, String value) {
        cookies.put(name, value);
    }

    public String getValue(String name) {
        return cookies.getOrDefault(name, "");
    }

    public String joinToString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(SEPARATOR));
    }
}
