package http;

import org.springframework.http.HttpMethod;

import java.util.Objects;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = HttpMethod.valueOf(method);
        this.path = path;
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }
}
