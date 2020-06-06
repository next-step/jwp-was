package http;

import java.util.Objects;

public class RequestLine {

    private final String method;
    private final String path;
    private final Protocol protocol;

    private RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine of(String method, String path, Protocol protocol) {
        return new RequestLine(method, path, protocol);
    }

    public static RequestLine of(String method, String path, String protocol, String version) {
        return RequestLine.of(method, path, Protocol.of(protocol, version));
    }

    public static RequestLine of(String line) {
        String[] values = line.trim().split(" ");
        Protocol protocol = Protocol.of(values[2]);
        return RequestLine.of(values[0], values[1], protocol);
    }

    String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
