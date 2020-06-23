package http.request;

import http.Headers;
import http.HttpCookies;

public class HttpRequestHeaders extends Headers {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";

    public int getContentLength() {
        final String value = getHeader(CONTENT_LENGTH);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public HttpCookies getCookies() {
        return HttpCookies.from(getHeader(COOKIE));
    }
}
