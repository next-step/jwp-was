package http;

import http.exception.BadRequestException;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {
    public static final String HEADER_KEY_VALUE_SPLITTER = ": ";
    public static final String HTTP_HEADER_LINE_JOINER = "\r\n";

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            throw new BadRequestException();
        }

        this.headers = headers;
    }

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    public boolean isNotEmpty() {
        return !headers.isEmpty();
    }

    @Override
    public String toString() {
        return headers.entrySet()
            .stream()
            .map(entry -> entry.getKey() + HEADER_KEY_VALUE_SPLITTER + entry.getValue())
            .collect(Collectors.joining(HTTP_HEADER_LINE_JOINER));
    }
}
