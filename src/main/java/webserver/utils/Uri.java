package webserver.utils;

import static exception.ExceptionStrings.INVALID_REQUEST_PATH;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Uri {
    private static final int PATH_COMPONENT_COUNT = 2;
    private static final String PATH_SPLIT_REGEX = "\\?";

    private String path;
    private QueryStrings queryStrings;

    public Uri(String path) {
        this(path, QueryStrings.empty());
    }

    public Uri(String path, QueryStrings queryStrings) {
        this.path = path;
        this.queryStrings = queryStrings;
    }

    public static Uri of(String path) {
        Uri.validate(path);

        String[] pathStrings = path.split(PATH_SPLIT_REGEX);
        if (pathStrings.length < PATH_COMPONENT_COUNT) {
            return new Uri(pathStrings[0]);
        }

        return new Uri(pathStrings[0], QueryStrings.of(pathStrings[1]));
    }

    private static void validate(String path) {
        Objects.requireNonNull(path, INVALID_REQUEST_PATH);

        if (path.isEmpty() || path.isBlank()) {
            throw new IllegalArgumentException(INVALID_REQUEST_PATH);
        }
    }

    public String getPath() {
        return path;
    }

    public boolean hasEmptyQueries() {
        return queryStrings.isEmpty();
    }

    public int sizeOfParams() {
        return queryStrings.size();
    }

    public String getParameter(String key) {
        return queryStrings.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Uri uri = (Uri) o;
        return Objects.equals(path, uri.path) && Objects.equals(queryStrings, uri.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryStrings);
    }
}
