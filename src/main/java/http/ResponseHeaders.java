package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static http.HttpHeader.CONTENT_TYPE;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

public class ResponseHeaders {

    private final Map<String, List<String>> responseHeaders;

    public ResponseHeaders() {
        this.responseHeaders = new HashMap<>();
    }

    public ResponseHeaders(final Map<String, List<String>> headers) {
        this.responseHeaders = headers;
    }

    public String getResponseHeaders() {
        String responseHeaders = this.responseHeaders.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + collectionToCommaDelimitedString(entry.getValue()))
                .collect(Collectors.joining("\r\n"));
        return responseHeaders;
    }

    public void addHeader(HttpHeader header, List<String> headerValues) {
        responseHeaders.put(header.getValue(), headerValues);
    }

    public String getContentType() {
        return collectionToCommaDelimitedString(responseHeaders.get(CONTENT_TYPE.getValue()));
    }
}
