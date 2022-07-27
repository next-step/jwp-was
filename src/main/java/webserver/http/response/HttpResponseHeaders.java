package webserver.http.response;

import webserver.http.header.HttpCookie;
import webserver.http.header.HttpCookies;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.http.header.HttpHeaderConstants.*;
import static webserver.http.response.HttpResponseMessage.RESPONSE_END_OF_LINE_MARKER;

public class HttpResponseHeaders {


    private Map<String, String> headers = new LinkedHashMap<>();
    private HttpCookies httpCookies = new HttpCookies();

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public static HttpResponseHeaders ofLocation(String locationPath) {
        HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
        httpResponseHeaders.addHeader(LOCATION, locationPath);

        return httpResponseHeaders;
    }

    public List<String> rawHeaders() {
        return headers.entrySet()
                .stream()
                .map(this::toRawHeader)
                .collect(Collectors.toList());
    }

    public String toRawHeader(Map.Entry<String, String> headerEntry) {
        return headerEntry.getKey() + HTTP_HEADER_DELIMITER + headerEntry.getValue() + RESPONSE_END_OF_LINE_MARKER;
    }

    public void addContentLengthHeader(int contentLength) {
        addHeader(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    public void addCookie(HttpCookie httpCookie) {
        httpCookies.addCookie(httpCookie);
        addHeader(SET_COOKIE, httpCookies.rawCookies());
    }
}
