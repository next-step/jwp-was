package webserver;

import java.util.Objects;

import http.request.HttpMethod;

public class Resource {

    private final String path;
    private final HttpMethod httpMethod;

    public Resource(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Resource resource = (Resource)o;
        return Objects.equals(path, resource.path) && httpMethod == resource.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, httpMethod);
    }
}
