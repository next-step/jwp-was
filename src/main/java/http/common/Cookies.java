package http.common;

import webserver.exceptions.IllegalCookieHeaderException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_TOKENIZER = ";";
    private static final String COOKIE_NAME_VALUE_TOKENIZER = "=";
    private static final String COOKIE_DEFAULT_VALUE = "";
    private static final String PATH = "path";

    private Map<String, String> cookies;
    private String path;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(String value) {
        cookies = new HashMap<>();

        for (String cookie : value.split(COOKIE_TOKENIZER)) {
            final String[] c = cookie.trim().split(COOKIE_NAME_VALUE_TOKENIZER);

            validateCookieTokenSize(c, value);

            final String cookieName = c[0].trim();
            final String cookieValue = c[1].trim();
            cookies.put(cookieName, cookieValue);
        }
    }

    private void validateCookieTokenSize(String[] c, String value) {
        if (c.length != 2) {
            throw new IllegalCookieHeaderException(value);
        }
    }

    public String getValue(String name) {
        return cookies.getOrDefault(name, COOKIE_DEFAULT_VALUE);
    }

    public void addCookie(String cookieName, String cookieValue) {
        cookies.put(cookieName, cookieValue);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> stringify() {
        List<String> cookiesStr = cookies.keySet().stream()
                .map(this::addCookie)
                .map(this::addPath)
                .map(builder -> builder.toString())
                .collect(Collectors.toList());

        return cookiesStr;
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    private StringBuilder addCookie(String cookieName) {
        return new StringBuilder(cookieName)
                .append(COOKIE_NAME_VALUE_TOKENIZER)
                .append(cookies.get(cookieName));
    }

    private StringBuilder addPath(StringBuilder cookie) {
        if (path == null) {
            return cookie;
        }
        return cookie
                .append(PATH)
                .append(COOKIE_NAME_VALUE_TOKENIZER)
                .append(path);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
