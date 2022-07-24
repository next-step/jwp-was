package webserver.http.response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.http.response.HttpResponseMessage.RESPONSE_END_OF_LINE_MARKER;

public class HttpResponseHeaders {
    private static final String RESPONSE_HEADER_DELIMITER = ":";

    private Map<String, String> headers = new LinkedHashMap<>();

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
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
}
