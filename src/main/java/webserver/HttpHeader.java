package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeader {

    private static final String HEADER_DELIMITER = ": ";
    private static final int NAME_IDX = 0;
    private static final int VALUE_IDX = 1;
    public static final String CONTENT_TYPE = "Content-Type";

    private Map<String, String> headers = new HashMap<>();
    private Cookie cookie;

    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public void addHeader(String headerLine) {
        if (hasHeader(headerLine)) {
            String[] split = headerLine.split(HEADER_DELIMITER);
            this.headers.put(split[NAME_IDX], split[VALUE_IDX]);
        }
    }

    public String getValue(String name) {
        if (headers.get(name) != null) {
            return headers.get(name).trim();
        }

        return "";
    }

    public boolean hasHeader(String headerLine) {
        if (headerLine != null && !headerLine.isBlank()) {
            return true;
        }
        return false;
    }
    public HttpHeader() {
        this.headers = headers;
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public Set<String> getKeySet() {
        return headers.keySet();
    }

    public void putCookie(Map<String, String> cookieData) {
        for (String key : cookieData.keySet()) {
            headers.put(key, cookie.getValue(key));
        }
    }
}
