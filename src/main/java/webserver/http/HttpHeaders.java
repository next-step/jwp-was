package webserver.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final String HOST_URL = "http://localhost:8080";
    private static final String HTTP_HEADER_DELIMITER = ": ";
    private static final int HEADER_PAIR_LIMIT = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders init() {
        return new HttpHeaders(new HashMap<>());
    }

    public static HttpHeaders redirect(String redirectUrl) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeader.LOCATION, HOST_URL + redirectUrl);
        headers.put(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");

        return new HttpHeaders(headers);
    }

    public void addRequestHeader(String headerLine) {
        if (headerLine == null) {
            throw new IllegalArgumentException();
        }

        String[] headerPair = headerLine.split(HTTP_HEADER_DELIMITER);

        if (headerPair.length == HEADER_PAIR_LIMIT) {
            headers.put(headerPair[KEY], headerPair[VALUE].trim());
        }
    }

    public void addResponseHeader(String httpHeader, String value) {
        this.headers.put(httpHeader, value);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(HttpHeader.CONTENT_LENGTH));
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public boolean hasContent() {
        return headers.containsKey(HttpHeader.CONTENT_LENGTH);
    }

    public boolean hasLocation() {
        return headers.containsKey(HttpHeader.LOCATION);
    }

    public boolean hasCookie() {
        return headers.containsKey(HttpHeader.COOKIE);
    }

    public List<String> getOutputHeaders() {
        return headers.keySet()
                .stream()
                .map(key -> key + HTTP_HEADER_DELIMITER + headers.get(key))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
