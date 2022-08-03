package model;

import java.util.Objects;

public class RequestMappingInfo {

    private HttpMethod method;
    private String path;

    public RequestMappingInfo(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return method == that.method && that.path.matches(path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
