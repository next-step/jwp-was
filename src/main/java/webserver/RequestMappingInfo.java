package webserver;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

import java.util.Objects;

public class RequestMappingInfo {

    private final String path;

    private final HttpMethod method;

    public RequestMappingInfo(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public boolean matchRequest(HttpRequest httpRequest) {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(httpRequest.getPath(), httpRequest.getMethod());

        if (this.equals(requestMappingInfo)) {
            return true;
        }

        return httpRequest.getPath().matches(this.path) && this.method.equals(httpRequest.getMethod());
    }

    @Override
    public String toString() {
        return String.format("url:[%s], method:[%s]", path, method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return Objects.equals(path, that.path) && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
