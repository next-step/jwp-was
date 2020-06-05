package http;

import java.util.Map;
import java.util.Objects;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            throw new IllegalArgumentException();
        }

        this.headers = headers;
    }

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }
}
