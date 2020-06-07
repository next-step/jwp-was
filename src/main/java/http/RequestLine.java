package http;

import utils.RequestHeaderUtils;

import java.util.Objects;

public class RequestLine {
    private final String method;
    private final String path;
    private final Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

//    public static RequestLine of(String value) {
//        return RequestHeaderUtils.parser(value);
//    }

//    public static RequestLine of(HttpMethod httpMethod, String path, Protocol protocol) {
//
//    }


    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Protocol getProtocol() {
        return Protocol.of(this.protocol.getProtocol(), this.protocol.getVersion());
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
