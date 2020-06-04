package http;

import java.util.Objects;

public class RequestMethodGet {

    private final String path;

    public RequestMethodGet(final String path) {
        this.path = path;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMethodGet that = (RequestMethodGet) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
