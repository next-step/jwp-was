package webserver.http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseHeader {
    private final Map<String, Object> headers;

    public ResponseHeader() {
        this.headers = new HashMap<>();
    }

    public ResponseHeader(Map<String, Object> headers) {
        this.headers = headers;
    }

    public ResponseHeader(String key, String value) {
        this.headers = new HashMap<>();
        this.headers.put(key, value);
    }

    public void add(String name, Object value) {
        this.headers.put(name, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeader header1 = (ResponseHeader) o;
        return Objects.equals(headers, header1.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
