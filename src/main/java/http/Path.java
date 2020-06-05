package http;

import java.util.Objects;

public class Path {

    private String path;
    private QueryString queryString;

    public Path(String value) {
        String[] values = value.split("\\?");
        this.path = values[0];
        if (isNotExistQueryString(values)) {
            return;
        }
        this.queryString = new QueryString(values[1]);
    }

    private boolean isNotExistQueryString(String[] values) {
        return values.length < 2;
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
