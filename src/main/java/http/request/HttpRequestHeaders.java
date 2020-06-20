package http.request;

import com.google.common.collect.Maps;
import http.HttpCookies;

import java.util.Collection;
import java.util.Map;

public class HttpRequestHeaders {
    private static final String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";
    private final Map<String, String> values = Maps.newHashMap();

    public HttpRequestHeaders() {
    }

    public void addHeader(final String header) {
        final String[] keyAndValue = header.trim().split(HEADER_DELIMITER);
        values.put(keyAndValue[0], keyAndValue[1]);
    }

    public void addHeader(final String key, final String value) {
        values.put(key, value);
    }

    public String getHeader(final String key) {
        return values.getOrDefault(key, null);
    }

    public int getContentLength() {
        final String value = getHeader(CONTENT_LENGTH);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public HttpCookies getCookies() {
        return HttpCookies.from(getHeader(COOKIE));
    }

    public Collection<String> getHeaderNames() {
        return values.keySet();
    }
}
