package webserver.http.request.header;

import webserver.http.header.HttpCookie;
import webserver.http.header.HttpCookies;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionId;
import webserver.http.session.HttpSessionStore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static webserver.http.header.HttpHeaderConstants.*;
import static webserver.http.session.HttpSessionStore.SESSION_ID_KEY;

public class HttpRequestHeaders {
    private static int HTTP_REQUEST_HEADER_SCHEMAS_MAXIMUM_SIZE = 2;
    private static int HTTP_REQUEST_HEADER_NAME_SCHEMA_INDEX = 0;
    private static int HTTP_REQUEST_HEADER_VALUE_SCHEMA_INDEX = 1;

    private Map<String, String> headers;
    private HttpCookies httpCookies = new HttpCookies();

    public HttpRequestHeaders(List<String> rawHeaders) {
        this.headers = parseRequestHeaderMap(rawHeaders);
    }

    private Map<String, String> parseRequestHeaderMap(List<String> rawHeaders) {
        Map<String, String> headers = new LinkedHashMap<>();
        for (String rawHeader : rawHeaders) {
            String[] httpRequestHeaderSchemas = rawHeader.split(HTTP_HEADER_DELIMITER, HTTP_REQUEST_HEADER_SCHEMAS_MAXIMUM_SIZE);

            String headerName = httpRequestHeaderSchemas[HTTP_REQUEST_HEADER_NAME_SCHEMA_INDEX];
            String headerValue = httpRequestHeaderSchemas[HTTP_REQUEST_HEADER_VALUE_SCHEMA_INDEX].trim();

            addHeader(headers, headerName, headerValue);
        }

        return headers;
    }

    private void addHeader(Map<String, String> headers, String headerName, String headerValue) {
        if (isCookieHeader(headerName)) {
            initializeCookies(headerValue);
        }

        headers.put(headerName, headerValue);
    }

    private boolean isCookieHeader(String headerName) {
        return COOKIE.equals(headerName);
    }

    private void initializeCookies(String rawCookieValue) {
        this.httpCookies = new HttpCookies(rawCookieValue);
    }

    public String get(String headerName) {
        return headers.get(headerName);
    }

    public HttpCookie getCookie(String cookieKey) {
        return httpCookies.getCookie(cookieKey);
    }

    public int contentLength() {
        String contentLengthHeader = headers.get(CONTENT_LENGTH);

        if (contentLengthHeader == null || contentLengthHeader.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(contentLengthHeader);
    }

    public HttpSession getSession() {
        HttpCookie sessionIdCookie = getCookie(SESSION_ID_KEY);

        if (sessionIdCookie.isNone()) {
            return HttpSessionStore.getSession(HttpSessionId.create());
        }

        return HttpSessionStore.getSession(HttpSessionId.of(sessionIdCookie.getCookieValue()));
    }
}
