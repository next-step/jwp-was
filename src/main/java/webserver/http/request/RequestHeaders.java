package webserver.http.request;

import webserver.http.Cookie;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class RequestHeaders {
    private final Map<String, String> headers;
    private final List<Cookie> cookies;

    public static Builder builder() {
        return new Builder();
    }

    private RequestHeaders(final Map<String, String> headers) {
        this.headers = headers;
        this.cookies = this.getCookies();
    }

    private List<Cookie> getCookies() {
        final String cookies = this.getOrNull("Cookie");

        if (cookies == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(cookies.split(";"))
                .filter(it -> !it.isBlank())
                .map(it -> it.trim().split("="))
                .map(it -> new Cookie(it[0], it[1]))
                .collect(Collectors.toList());
    }

    public String get(final String headerName) {
        final String valueOrNull = this.getOrNull(headerName);

        if (valueOrNull == null) {
            throw new IllegalArgumentException("Header not found: " + headerName);
        }

        return valueOrNull;
    }

    public String getOrNull(final String headerName) {
        return this.headers.get(headerName);
    }

    public boolean hasRequestBody() {
        return this.headers.containsKey("Content-Length");
    }

    public String getHost() {
        return this.get("Host");
    }

    public String getConnection() {
        return this.get("Connection");
    }

    public int getContentLength() {
        return Integer.parseInt(this.get("Content-Length"));
    }

    public String getContentType() {
        return this.get("Content-Type");
    }

    public String getAccept() {
        return this.get("Accept");
    }

    public Cookie getCookie(final String cookieName) {
        final Cookie cookie = this.getCookieOrNull(cookieName);

        if (cookie == null) {
            throw new IllegalArgumentException("Cannot get Cookie: " + cookieName);
        }

        return cookie;
    }

    public Cookie getCookieOrNull(final String cookieName) {
        return this.cookies.stream()
                .filter(it -> it.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }

    // RequestHeaders.Builder
    public static class Builder {
        private final List<String> headerNameAndValues = new ArrayList<>();

        public Builder add(final String headerNameAndValue) {
            this.headerNameAndValues.add(headerNameAndValue);
            return this;
        }

        public RequestHeaders build() {
            final Map<String, String> headers = this.headerNameAndValues.stream()
                    .map(it -> it.split(": "))
                    .collect(toMap(
                            it -> it[0],
                            it -> it[1]
                    ));
            return new RequestHeaders(headers);
        }
    }
}
