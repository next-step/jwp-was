package domain;

import java.util.Objects;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocol httpProtocol;

    public HttpRequest(HttpMethod httpMethod, HttpPath httpPath, HttpProtocol httpProtocol) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.httpProtocol = httpProtocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(httpPath, that.httpPath) && Objects.equals(httpProtocol, that.httpProtocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpPath, httpProtocol);
    }
}
