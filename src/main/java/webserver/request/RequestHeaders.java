package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeaders {

    private static final String HEADER_DELIMITER = ": ";
    private static final String HOST = "Host";
    private static final String CONNECTION = "Connection";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String COOKIE = "Cookie";

    private final Map<String, String> headers = new HashMap<>();
    private final Cookies cookies = new Cookies();

    public RequestHeaders() {
    }

    public RequestHeaders(Map<String, String> headers) {
        if (headers.containsKey(COOKIE)) {
            this.cookies.addCookies(headers.get(COOKIE));
        }

        this.headers.putAll(headers);
    }

    public void add(final String header) {
        if (header == null || header.isBlank()) {
            return;
        }

        final String[] keyAndValue = header.split(HEADER_DELIMITER);
        if (keyAndValue.length == 1) {
            return;
        }

        if (COOKIE.equals(keyAndValue[0].trim())) {
            this.cookies.addCookies(keyAndValue[1].trim());
        }

        headers.put(keyAndValue[0].trim(), keyAndValue[1].trim());
    }

    public String getHost() {
        return headers.get(HOST);
    }

    public String getConnection() {
        return headers.get(CONNECTION);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    public String getAccept() {
        return headers.get(ACCEPT);
    }

    public String get(final String key) {
        return headers.get(key);
    }

    public boolean hasRequestBody() {
        return headers.containsKey(CONTENT_LENGTH) && getContentLength() > 0;
    }

    public boolean hasCookie(final String cookieName) {
        return cookies.hasCookie(cookieName);
    }

    public void addCookie(final String cookie) {
        cookies.addCookies(cookie);
    }

    public String getCookie(final String cookieName) {
        return cookies.getCookie(cookieName);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestHeaders that = (RequestHeaders) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
