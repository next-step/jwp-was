package webserver.http;

import java.util.Objects;

public class RequestLine {

    public static final String REQUEST_SPLIT_SYMBOL = " ";
    private final String method;
    private final String path;

    private final Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public RequestLine(String request) {
        String[] splitRequest = request.split(REQUEST_SPLIT_SYMBOL);
        this.method = splitRequest[0];
        this.path = splitRequest[1];
        this.protocol = new Protocol(splitRequest[2]);
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }

    public String protocol() {
        return protocol.protocol();
    }

    public String version() {
        return protocol.version();
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
