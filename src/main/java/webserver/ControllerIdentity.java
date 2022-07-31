package webserver;

import java.util.Objects;

import http.request.HttpMethod;

public class ControllerIdentity {

    private final String path;
    private final HttpMethod httpMethod;

    public ControllerIdentity(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ControllerIdentity controllerIdentity = (ControllerIdentity)o;
        return Objects.equals(path, controllerIdentity.path) && httpMethod == controllerIdentity.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, httpMethod);
    }
}
