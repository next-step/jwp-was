package webserver;

import java.util.Objects;

public class Uri {

    private static final String URI_DELIMITER = "\\?";

    private final String path;
    private final HttpParameter queryString;

    private Uri(String path, HttpParameter queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Uri from(String input) {
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException();
        }

        String[] split = input.split(URI_DELIMITER);

        if (split.length <= 1) {
            return new Uri(split[0], new HttpParameter());
        }

        return new Uri(split[0], HttpParameter.from(split[1]));
    }

    public String getPath() {
        return path;
    }

    public HttpParameter getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uri uri = (Uri) o;
        return Objects.equals(path, uri.path) && Objects.equals(queryString, uri.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }
}
