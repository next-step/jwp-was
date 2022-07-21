package webserver.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Path {

    private static final String BLANK = " ";
    private static final int PATH_PLACE_IDX = 1;

    private String path;
    private String queryString;

    public Path(String path) {
        this.path = path;
    }

    public Path(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path parse(String requestLine) {
        String[] values = requestLine.split(BLANK);

        Path path = getQueryString(values);
        if (path != null) return path;

        return new Path(values[PATH_PLACE_IDX]);
    }

    private static Path getQueryString(String[] values) {
        String[] paths = values[PATH_PLACE_IDX].split("\\?");
        if(paths.length >= 2) {
            return new Path(paths[0], paths[1]);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(getPath(), path1.getPath()) && Objects.equals(getQueryString(), path1.getQueryString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getQueryString());
    }
}
