package http;

import java.util.Objects;

public class RequestLine {
    private String method;
    private String path;
    private Protocol protocol;
    private QueryString queryString;

    public RequestLine(String method, String path, Protocol protocol, QueryString queryString) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.queryString = queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, queryString);
    }
}
