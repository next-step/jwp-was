package http;

import java.util.Map;
import java.util.Objects;

public class Path {
    private String path;
    private QueryStrings queryStrings;

    public Path(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings.getQueryStrings();
    }
}
