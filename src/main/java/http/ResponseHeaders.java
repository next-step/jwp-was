package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

public class ResponseHeaders {

    private final Map<String, List<String>> responseHeaders;

    public ResponseHeaders() {
        this.responseHeaders = new HashMap<>();
    }

    public String getResponseHeaders() {
        String responseHeaders = this.responseHeaders.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + collectionToCommaDelimitedString(entry.getValue()))
                .collect(Collectors.joining("\r\n"));
        return responseHeaders;
    }

    public void addHeader(HttpHeader header, List<String> headerValues) {
        List<String> values = responseHeaders.getOrDefault(header, Collections.emptyList());
        headerValues.addAll(values);
        responseHeaders.put(header.getValue(), headerValues);
    }

    public List<String> get(final HttpHeader httpHeader) {
        return responseHeaders.getOrDefault(httpHeader, Collections.emptyList());
    }
}
