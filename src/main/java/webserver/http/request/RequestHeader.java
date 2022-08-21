package webserver.http.request;

import webserver.http.Cookie;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class RequestHeader {
    private static final String HEADER_SEPARATOR = ": ";

    private final Map<String, Object> headers;
    private final Cookie cookie;

    public RequestHeader(List<String> headerMap) {
        this.headers = headerMap.stream()
                .map(request -> request.split(HEADER_SEPARATOR))
                .collect(toMap(s -> s[0], s -> s[1]));
        this.cookie = new Cookie();
    }

    public RequestHeader(Map<String, Object> header) {
        this.headers = header;
        this.cookie = new Cookie();
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void addHeader(String key, Object value) {
        this.headers.put(key, value);
    }

    public int getContentLength() {
        return headers.get("Content-Length") == null ? 0 : Integer.parseInt(headers.get("Content-Length").toString());
    }

    public String getCookieValue(String key) {
        return this.cookie.getCookie(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader header1 = (RequestHeader) o;
        return Objects.equals(headers, header1.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    public void setCookie(String key, Object value) {
        this.cookie.setCookie(key, value);
    }
}
