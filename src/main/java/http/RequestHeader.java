package http;

import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();

    public RequestHeader(final String header) {
        Tokens tokens = Tokens.init(header, "\n");
        tokens.getAllTokens()
                .forEach(this::addHeader);
    }

    private void addHeader(final String token) {
        Tokens tokens = Tokens.init(token, ":");
        String key = tokens.nextToken().trim();
        String value = tokens.nextToken().trim();

        if (key.startsWith("Cookie")) {
            initCookies(value);
            return;
        }

        headers.put(key, value);
    }

    private void initCookies(final String cookies) {
        Tokens tokens = Tokens.init(cookies, ";");

        tokens.getAllTokens()
                .forEach(this::addCookie);
    }

    private void addCookie(final String token) {
        Tokens tokens = Tokens.init(token, "=");
        if (tokens.getSize() < 2) {
            return;
        }

        cookies.put(tokens.nextToken().trim(), tokens.nextToken().trim());
    }

    public String getHeader(final String headerName) {
        return headers.getOrDefault(headerName, null);
    }

    public String getCookie(final String cookieName) {
        return cookies.get(cookieName);
    }
}
