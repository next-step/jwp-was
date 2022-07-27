package webserver.http.response;

import java.util.Map;
import java.util.Objects;

public class ResponseHeader {
    private final Map<String, String> headers;

    public ResponseHeader(Map<String, String> header) {
        this.headers = header;
    }

    public void add(Map<String, String> headers) {
        this.headers.putAll(headers);
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
