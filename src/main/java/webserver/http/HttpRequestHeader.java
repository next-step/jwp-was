package webserver.http;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestHeader {
    private static final String DEFAULT_CONTENT_LENGTH = "0";

    private Map<HttpHeaders, String> headers;
    private HttpCookie httpCookie;

    private HttpRequestHeader(Map<HttpHeaders, String> headers) {
        this.headers = headers;
        this.httpCookie = HttpCookie.empty();
    }

    private HttpRequestHeader(Map<HttpHeaders, String> headers, HttpCookie httpCookie) {
        this.headers = headers;
        this.httpCookie = httpCookie;
    }

    public static HttpRequestHeader of(List<String> headers) {
        Map<HttpHeaders, String> headerMap = createHeaderMap(headers);
        if (headerMap.containsKey(HttpHeaders.COOKIE)) {
            String cookie = headerMap.get(HttpHeaders.COOKIE);
            HttpCookie httpCookie = HttpCookie.of(cookie);
            return new HttpRequestHeader(headerMap, httpCookie);
        }
        return new HttpRequestHeader(headerMap);
    }

    private static Map<HttpHeaders, String> createHeaderMap(List<String> headers) {
        return headers.stream()
                .map(header -> header.split(": "))
                .collect(Collectors.toMap(key -> HttpHeaders.of(key[0]), value -> value[1]));
    }

    public Map<HttpHeaders, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(HttpHeaders.CONTENT_LENGTH, DEFAULT_CONTENT_LENGTH));
    }

    public boolean containsLoginCookie() {
        return httpCookie != null && httpCookie.contains("logined=true");
    }

    public HttpCookie getCookie() {
        return httpCookie;
    }
}
