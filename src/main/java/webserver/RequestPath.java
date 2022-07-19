package webserver;

import java.util.Objects;

public class RequestPath {

    private final String path;

    RequestPath(String path) {
        validatePath(path);
        this.path = path;
    }

    private void validatePath(String path) {
        if (path != null && !path.startsWith("/")) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 RequestPath 가 아님", path));
        }
    }

    String getQueryString() {
        String[] split = path.split("\\?");

        if (split.length < 2) {
            return "";
        }

        return split[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPath that = (RequestPath) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
