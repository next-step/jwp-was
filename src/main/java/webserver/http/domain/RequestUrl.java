package webserver.http.domain;

import webserver.http.enums.HTTPMethod;

import java.util.Map;
import java.util.Objects;

public class RequestUrl {

    public static final String REQUEST_SPLIT_SYMBOL = " ";
    private final HTTPMethod method;
    private final Path path;

    private final Protocol protocol;

    public RequestUrl(HTTPMethod method, String path, Protocol protocol) {
        this.method = method;
        this.path = Path.create(path);
        this.protocol = protocol;
    }

    public RequestUrl(String request) {
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

    public boolean samePath(String path) {
        return path().equals(path);
    }

    public boolean isGetMethod() {
        return method.equals(HTTPMethod.GET);
    }

    public boolean isPostMethod() {
        return method.equals(HTTPMethod.POST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUrl that = (RequestUrl) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
