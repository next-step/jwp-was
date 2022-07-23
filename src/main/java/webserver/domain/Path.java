package webserver.domain;

import lombok.Getter;
import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

import java.util.Objects;

@Getter
public class Path {

    private static final int PATH_PLACE_IDX = 1;
    private static final String DELIMITER = "\\?";

    private String path;
    private String queryString;

    public Path(String path) {
        this.path = path;
    }

    public Path(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path parse(String pathInfo) {
        validate(pathInfo);

        String[] paths = pathInfo.split(DELIMITER);
        if(paths.length >= 2) {
            return new Path(paths[0], paths[1]);
        }
        return new Path(paths[0]);
    }

    private static void validate(String pathInfo) {
        if (!StringUtils.hasText(pathInfo))
            throw new StringEmptyException("pathinfo is empty");
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
