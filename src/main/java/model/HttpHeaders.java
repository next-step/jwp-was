package model;

import types.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

    public static final String SESSION_HEADER_KEY = "JSESSIONID";
    private static final String COOKIE_HEADER_KEY = "Cookie";
    private final String HEADER_KEY_VALUE_SEPARATOR = ": ";

    private final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(List<String> httpMessageData) {
        this.headers = new HashMap<>();
        if (httpMessageData.stream().anyMatch(data -> !data.contains(HEADER_KEY_VALUE_SEPARATOR))) {
            throw new IllegalArgumentException();
        }

        httpMessageData
                .forEach(data -> {
                    String[] values = data.split(HEADER_KEY_VALUE_SEPARATOR);
                    String key = values[0];
                    String value = values[1];
                    headers.put(key, value);
                });
    }

    public String getInfo() {
        StringBuilder data = new StringBuilder();
        data.append("RequestHeaders : \n");
        this.headers.forEach((key, value) -> {
            data.append(String.format("key = %s || value = %s", key, value));
            data.append("\n");
        });

        return data.toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setLocation(String url) {
        this.headers.put("Location", url);
    }

    public void setContentType(MediaType mediaType) {
        this.headers.put("Content-Type", mediaType.getValue());
    }

    public void setCookie(String value) {
        this.headers.put("Set-Cookie", value);
    }

    public void setSessionIdInCookie(String value) {
        this.setCookie(SESSION_HEADER_KEY + COOKIE_KEY_VALUE_SEPARATOR + value);
    }

    public Cookie getCookie() {
        String cookieValues = this.headers.get(COOKIE_HEADER_KEY);
        if (cookieValues == null) {
            return null;
        }

        return new Cookie(cookieValues);
    }

    public String getSessionId() {
        Cookie cookie = this.getCookie();
        if (cookie == null) {
            return null;
        }

        return cookie.getValue(SESSION_HEADER_KEY);
    }
}
