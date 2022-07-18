package utils.http;

import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private String protocol;
    private String version;
    private Map<String, String> params;

    public HttpRequest(HttpMethod method,
                       String path,
                       String protocol,
                       String version,
                       Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return method == that.method && path.equals(that.path) && protocol.equals(that.protocol) && version.equals(that.version) && params.equals(that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version, params);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", params=" + params +
                '}';
    }
}
