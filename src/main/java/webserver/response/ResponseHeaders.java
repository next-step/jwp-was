package webserver.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseHeaders {

    private final Map<String, String> headers = new HashMap<>();

    public ResponseHeaders() {
    }

    public ResponseHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void add(final String key, final String value) {
        if (key == null || key.isBlank()) {
            return;
        }
        if (value == null || value.isBlank()) {
            return;
        }
        headers.put(key, value);
    }

    public String get(final String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResponseHeaders that = (ResponseHeaders) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
