package http;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestHeader {
    private static final String COOKIE = "Cookie";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String HEADER_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = ";";
    public static final String NEW_LINE = "\n";

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();
    private StringBuilder origin = new StringBuilder();

    private RequestHeader() {}

    public static RequestHeader init() {
        return new RequestHeader();
    }

    public RequestHeader(final String header) {
        origin.append(header).append('\n');
        StringTokenizer tokens = new StringTokenizer(header, NEW_LINE);

        while (tokens.hasMoreTokens()) {
            addHeader(tokens.nextToken());
        }
    }

    public void addHeader(final String token) {
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
        StringTokenizer tokens = new StringTokenizer(cookies, COOKIE_DELIMITER);

        while (tokens.hasMoreTokens()) {
            addCookie(tokens.nextToken());
        }
    }

    private void addCookie(final String token) {
        String[] tokens = token.trim()
                .split(COOKIE_KEY_VALUE_DELIMITER);

        if (tokens.length < 2) {
            return;
        }

        cookies.put(tokens[0].trim(), tokens[1].trim());
    }

    public String getHeader(final String headerName) {
        return headers.get(headerName);
    }

    public String getCookie(final String cookieName) {
        return cookies.get(cookieName);
    }

    public Map<String, String> getParameters() {
        return headers;
    }

    @Override
    public String toString() {
        return origin.toString();
    }
}
