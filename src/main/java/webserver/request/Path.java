package webserver.request;

import java.util.Objects;

public class Path {

    private final String path;

    private Path(String path) {
        this.path = path;
    }

    public static Path from(String pathStr) {
        return new Path(pathStr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
