package http.request;

import java.util.Objects;

public class Path {

    private static final String PATH_DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;

    private String path;
    private QueryString queryString;

    public Path(String value) {
        String[] values = value.split(PATH_DELIMITER);
        this.path = values[PATH_INDEX];
        if (isExistQueryString(values)) {
            this.queryString = new QueryString(values[QUERY_STRING_INDEX]);
        }
    }

    private boolean isExistQueryString(String[] values) {
        return values.length > 1;
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
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) &&
                Objects.equals(queryString, path1.queryString);
    }

    public boolean isExistQuery() {
        return queryString != null;
    }

    public void addQueryString(String queryString) {
        this.queryString = new QueryString((queryString));
    }
}
