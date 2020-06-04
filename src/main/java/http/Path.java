package http;

import java.util.Map;
import java.util.Objects;

public class Path {
    private String path;
    private QueryStrings queryStrings = new QueryStrings();

    public Path(String path) {
        this.path = getPathOnly(path);
        if (hasQueryStrings(path)) {
            this.queryStrings = new QueryStrings(path);
        }
    }

    public String getPath() {
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