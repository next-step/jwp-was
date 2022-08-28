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

    public ResponseHeader(String key, Object value) {
        this.headers = new HashMap<>();
        this.headers.put(key, value);
    }

    public void setHeader(String name, Object value) {
        this.headers.put(name, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Object getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeader that = (ResponseHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "headers=" + headers +
                '}';
    }
}
