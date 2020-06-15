package http;

import java.util.Objects;

public class RequestPath {
    private final String path;

    public RequestPath(String path) {
        this.path = path;
    }

    public static RequestPath of(String path) {
        if (!path.contains("?")) {
            return new RequestPath(path);
        }
        return new RequestPath(path.split("\\?")[0]);
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestPath)) return false;
        RequestPath that = (RequestPath) o;
        return Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath());
    }

    @Override
    public String toString() {
        return "RequestPath{" +
                "path='" + path + '\'' +
                '}';
    }
}
