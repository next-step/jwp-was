package webserver.http;

import java.util.HashMap;
import java.util.Objects;

public class Path {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final int NOT_FOUND = -1;

    private final String path;

    private final Params params;

    private Path(String path) {
        this(path, Params.from(new HashMap<>()));
    }

    private Path(String path, Params params) {
        this.path = path;
        this.params = params;
    }

    public static Path createWithGetMethod(String value) {
        int paramIndex = value.indexOf(QUERY_STRING_DELIMITER);
        if (paramIndex == NOT_FOUND && value.equals("/")) {
            return new Path("/index.html");
        }
        if (paramIndex == NOT_FOUND) {
            return new Path(value);
        }
        final String path = value.substring(0, paramIndex);
        final Params params = Params.from(value.substring(paramIndex + 1));
        return new Path(path, params);
    }

    public static Path createWithPost(String value) {
        return new Path(value, null);
    }

    public boolean isSameUrlPath(String path) {
        return this.path.equals(path);
    }

    public String getPath() {
        return path;
    }

    public Params getParams() {
        return params;
    }

    public boolean endWith(String suffix) {
        return path.endsWith(suffix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) && Objects.equals(params, path1.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, params);
    }
}
