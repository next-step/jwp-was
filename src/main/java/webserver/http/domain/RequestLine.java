package webserver.http.domain;

import webserver.http.enums.HTTPMethod;

import java.util.Map;
import java.util.Objects;

public class RequestLine {

    public static final String REQUEST_SPLIT_SYMBOL = " ";
    private final HTTPMethod method;
    private final Path path;

    private final Protocol protocol;

    public RequestLine(HTTPMethod method, String path, Protocol protocol) {
        this.method = method;
        this.path = Path.create(path);
        this.protocol = protocol;
    }

    public RequestLine(String request) {
        String[] splitRequest = request.split(REQUEST_SPLIT_SYMBOL);
        this.method = HTTPMethod.httpMethod(splitRequest[0]);
        this.path = Path.create(splitRequest[1]);
        this.protocol = new Protocol(splitRequest[2]);
    }

    public HTTPMethod method() {
        return method;
    }

    public String path() {
        return path.path();
    }

    public String protocol() {
        return protocol.protocol();
    }

    public String version() {
        return protocol.version();
    }

    public Map<String, String> requestParams() {
        return path.requestParams();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
