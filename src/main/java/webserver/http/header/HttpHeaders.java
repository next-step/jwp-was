package webserver.http.header;

import utils.StringUtils;
import webserver.http.HeaderName;
import webserver.http.cookie.Cookies;
import webserver.http.cookie.HttpCookies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class HttpHeaders implements Headers {

    private static final int DEFAULT_CONTENT_LENGTH = 0;

    private final Map<String, String> headers;

    private HttpHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static Headers of(final String rawHttpHeaders) {
        if (StringUtils.isBlank(rawHttpHeaders)) {
            return empty();
        }

        return Arrays.stream(rawHttpHeaders.split(lineSeparator()))
                .map(HttpHeader::of)
                .collect(collectingAndThen(toMap(HttpHeader::getKey, HttpHeader::getValue),
                        HttpHeaders::new));
    }

    public static Headers empty() {
        return new HttpHeaders(new HashMap<>());
    }

    @Override
    public void add(final String key,
                    final String value) {
        headers.put(key, value);
    }

    @Override
    public void setLocation(final String redirectPath) {
        add(HeaderName.LOCATION, redirectPath);
    }

    @Override
    public void setContentLength(final int contentLength) {
        add(HeaderName.CONTENT_LENGTH, String.valueOf(contentLength));
    }

    @Override
    public Cookies getCookies() {
        return HttpCookies.of(getString(HeaderName.COOKIE.toString()));
    }

    @Override
    public String getString(final String key) {
        return headers.get(key);
    }

    @Override
    public int getContentLength() {
        try {
            return parseInt(getString(HeaderName.CONTENT_LENGTH.toString()));
        } catch (final NumberFormatException ignore) { }

        return DEFAULT_CONTENT_LENGTH;
    }

    @Override
    public boolean isEmpty() {
        return headers.isEmpty();
    }

    @Override
    public List<HttpHeader> toList() {
        return headers.entrySet()
                .stream()
                .map(entry -> HttpHeader.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private void add(final HeaderName key,
                     final String value) {
        add(key.toString(), value);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
