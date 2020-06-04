package http;

import java.util.Objects;

public class RequestMethodPost implements RequestMethod {
    private static final String METHOD_NAME = "POST";

    private final String path;

    public RequestMethodPost(final String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMethodPost that = (RequestMethodPost) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
