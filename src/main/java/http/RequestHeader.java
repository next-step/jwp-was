package http;

import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final String COOKIE = "Cookie";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String HEADER_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = ";";

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();

    public RequestHeader(final String header) {
        Tokens tokens = Tokens.init(header, "\n");
        tokens.getAllTokens()
                .forEach(this::addHeader);
    }

    private void addHeader(final String token) {
        String[] tokens = token.split(HEADER_DELIMITER);
        String key = tokens[0];
        String value = tokens[1];

        if (COOKIE.equals(key)) {
            initCookies(value);
            return;
        }

        headers.put(key, value);
    }

    private void initCookies(final String cookies) {
        Tokens tokens = Tokens.init(cookies, COOKIE_DELIMITER);

        tokens.getAllTokens()
                .forEach(this::addCookie);
    }

    private void addCookie(final String token) {
        String[] tokens = token.split(COOKIE_KEY_VALUE_DELIMITER);

        if (tokens.length < 2) {
            return;
        }

        cookies.put(tokens[0], tokens[1]);
    }

    public String getHeader(final String headerName) {
        return headers.get(headerName);
    }

    public String getCookie(final String cookieName) {
        return cookies.get(cookieName);
    }
}
