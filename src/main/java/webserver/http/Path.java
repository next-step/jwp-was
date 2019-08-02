package webserver.http;

import java.util.Optional;

public class Path {

    private final String uri;
    private final String queryString;

    public Path(String uri) {
        this(uri, null);
    }

    public Path(String uri, String queryString) {
        this.uri = uri;
        this.queryString = queryString;
    }

    public static Path from(String path) {
        if (!path.contains("?")) {
            return new Path(path);
        }

        String[] values = path.split("\\?");
        return new Path(values[0], values[1]);
    }

    public String getUri() {
        return this.uri;
    }

    public Optional<String> getQueryString() {
        return Optional.ofNullable(this.queryString);
    }
}
