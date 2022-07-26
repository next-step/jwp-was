package webserver.http.response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.http.response.HttpResponseMessage.RESPONSE_END_OF_LINE_MARKER;

public class HttpResponseHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    private static final String RESPONSE_HEADER_DELIMITER = ": ";

    private Map<String, String> headers = new LinkedHashMap<>();

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public static HttpResponseHeaders ofLocation(String locationPath) {
        HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
        httpResponseHeaders.addHeader(LOCATION, locationPath);

        return httpResponseHeaders;
    }

    public void addHtmlContentTypeHeader() {
        addHeader(CONTENT_TYPE, TEXT_HTML_CHARSET_UTF_8);
    }

    public List<String> rawHeaders() {
        return headers.entrySet()
                .stream()
                .map(this::toRawHeader)
                .collect(Collectors.toList());
    }

    public String toRawHeader(Map.Entry<String, String> headerEntry) {
        return headerEntry.getKey() + RESPONSE_HEADER_DELIMITER + headerEntry.getValue() + RESPONSE_END_OF_LINE_MARKER;
    }

    public void addContentLengthHeader(int contentLength) {
        addHeader(CONTENT_LENGTH, String.valueOf(contentLength));
    }
}
