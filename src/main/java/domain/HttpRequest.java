package domain;

import java.util.Objects;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public HttpRequest(String method, String path, String protocol, String version) {

        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version);
    }
}
