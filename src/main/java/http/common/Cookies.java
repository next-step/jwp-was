package http.common;

import org.apache.logging.log4j.util.Strings;
import webserver.exceptions.IllegalCookieHeaderException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_TOKENIZER = ";";
    private static final String COOKIE_JOIN_DELIMITER = "; ";
    private static final String COOKIE_NAME_VALUE_TOKENIZER = "=";
    private static final String COOKIE_DEFAULT_VALUE = Strings.EMPTY;

    private Map<String, String> cookies;
    private String path = Strings.EMPTY;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(String values) {
        cookies = new HashMap<>();
        for (String cookie : values.split(COOKIE_TOKENIZER)) {
            final String[] c = cookie.trim().split(COOKIE_NAME_VALUE_TOKENIZER);
            if (c.length != 2) {
                throw new IllegalCookieHeaderException(values);
            }
            final String cookieName = c[0].trim();
            final String cookieValue = c[1].trim();
            cookies.put(cookieName, cookieValue);
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

    public String stringify() {
        String cookiesStr = cookies.keySet().stream()
                .map(cookieName -> (cookieName + "="+cookies.get(cookieName)))
                .collect(Collectors.joining(COOKIE_JOIN_DELIMITER));

        if (!Strings.EMPTY.equals(path)) {
            cookiesStr += COOKIE_JOIN_DELIMITER + "Path=" + path;
        }
        return cookiesStr;
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
