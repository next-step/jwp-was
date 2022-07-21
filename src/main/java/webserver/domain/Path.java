package webserver.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Path {

    private static final String BLANK = " ";

    private static final int PATH_PLACE_IDX = 1;

    private String path;

    public Path(String path) {
        this.path = path;
    }

    public static Path parse(String requestLine) {
        String[] values = requestLine.split(BLANK);
        return new Path(values[PATH_PLACE_IDX]);
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
}
