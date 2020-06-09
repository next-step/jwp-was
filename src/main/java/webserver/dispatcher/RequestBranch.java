package webserver.dispatcher;

import http.requests.types.HttpMethod;

import java.util.Objects;

public class RequestBranch {

    private final String path;
    private final HttpMethod method;

    public RequestBranch(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBranch that = (RequestBranch) o;
        return Objects.equals(path, that.path) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}