package webserver.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookies {

    private static final String DELIMITER_FOR_COOKIES = ";";
    private static final String DELIMITER_FOR_COOKIE = "=";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;

    private final Map<String, String> cookies = new HashMap<>();

    public Cookies() {
    }

    public Cookies(String cookies) {
        parseCookies(cookies);
    }

    private void parseCookies(String cookies) {
        if (cookies == null || cookies.isBlank()) {
            return;
        }

        String[] tokens = cookies.split(DELIMITER_FOR_COOKIES);
        Arrays.stream(tokens).forEach(token -> {
            String[] nameValuePair = token.trim().split(DELIMITER_FOR_COOKIE);
            this.cookies.put(nameValuePair[INDEX_ZERO], nameValuePair[INDEX_ONE]);
        });
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public void add(String name, String value) {
        cookies.put(name, value);
    }

    public List<String> getCookiesAsString() {
        List<String> values = new ArrayList<>();
        cookies.forEach((key, value) -> values.add(String.format("%s=%s", key, value)));
        return values;
    }
}
