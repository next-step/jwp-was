package http.requestline;

import http.QueryStrings;

import java.util.Map;
import java.util.Objects;

public class Path {
    private String path;
    private QueryStrings queryStrings;

    public Path(String path) {
        this.path = getPathOnly(path);
        extractQueryStrings(path);
    }

    public String getStringPath() {
        return path;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings.getQueryStrings();
    }

    private boolean hasQueryStrings(String path) {
        String[] split = path.split("\\?");
        return split.length == 2;
    }

    private String getPathOnly(String path) {
        String[] split = path.split("\\?");
        return split[0];
    }

    private void extractQueryStrings(String path) {
        if (hasQueryStrings(path)) {
            this.queryStrings = new QueryStrings(path);
        }
        if (!hasQueryStrings(path)) {
            this.queryStrings = new QueryStrings();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) &&
                Objects.equals(queryStrings, path1.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryStrings);
    }
}