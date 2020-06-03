package webserver;

import java.util.Objects;

public class RequestLine {

    private String method;
    private String path;
    private Protocol protocol;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = new Protocol(protocol, version);
    }

    public RequestLine(String s, String s1, Protocol protocol) {
        this.method = s;
        this.path = s1;
        this.protocol = protocol;
    }

    public static RequestLine of(String requestLine) {
        String[] methodAndPath = requestLine.split(" ");
        Protocol protocol = new Protocol(methodAndPath[2]);
        return new RequestLine(methodAndPath[0], methodAndPath[1], protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }
}
