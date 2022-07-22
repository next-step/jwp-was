package webserver;

import webserver.http.HttpMethod;
import webserver.http.Path;
import webserver.http.Protocol;

import java.util.Objects;

import static model.Constant.VALUE_SPERATOR;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, Path path, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public RequestLine(String requestValue) {
        String[] values = requestValue.split(VALUE_SPERATOR);

        this.httpMethod = HttpMethod.valueOf(values[0]);
        this.path = new Path(values[1]);
        this.protocol = new Protocol(values[2]);
    }

    public RequestLine(String method, String path, String protocol) {
        this(HttpMethod.valueOf(method), new Path(path), new Protocol(protocol));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }
}
