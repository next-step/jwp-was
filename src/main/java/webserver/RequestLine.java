package webserver;

import javax.annotation.Nullable;
import java.util.Objects;

public class RequestLine {
    private final String httpMethod;
    private final String path;
    private final String protocol;
    private final String version;

    @Nullable
    private final String queryString;

    public RequestLine(String httpMethod, String path, String protocol, String version, @Nullable String queryString) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.queryString = queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version) && Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol, version, queryString);
    }
}
