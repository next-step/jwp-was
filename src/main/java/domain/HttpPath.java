package domain;

import java.util.Objects;

public class HttpPath {
    private final String path;

    public HttpPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpPath httpPath = (HttpPath) o;
        return Objects.equals(path, httpPath.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
