package webserver.http.request;


import utils.Assert;

import java.util.Objects;

public final class Path {

    private static final String ROOT_PATH_STRING = "/";

    private final String urlPath;

    private Path(String urlPath) {
        Assert.notNull(urlPath, "'path' must not be null");
        this.urlPath = urlPath;
    }

    public static Path from(String path) {
        if (path == null || path.isBlank() || ROOT_PATH_STRING.equals(path.trim())) {
            return root();
        }
        return new Path(path);
    }

    public static Path root() {
        return new Path(ROOT_PATH_STRING);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path1 = (Path) o;
        return Objects.equals(urlPath, path1.urlPath);
    }

    @Override
    public String toString() {
        return urlPath;
    }
}
