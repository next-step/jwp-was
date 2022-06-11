package webserver;

import java.util.Objects;

public class Uri {

    private static final String URI_DELIMITER = "\\?";

    private final String path;
    private final QueryString queryString;

    private Uri(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Uri from(String input) {
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException();
        }

        String[] split = input.split(URI_DELIMITER);

        if (split.length <= 1) {
            return new Uri(split[0], new QueryString());
        }

        return new Uri(split[0], QueryString.from(split[1]));
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
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
