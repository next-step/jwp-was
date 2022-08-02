package webserver.request.domain.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

import java.util.Objects;

public class Path {

    private static final String DELIMITER = "\\?";

    private String path;
    private QueryString queryString = null;

    public Path(String path) {
        this.path = path;
    }

    public Path(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path parse(String pathInfo) {
        validate(pathInfo);

        String[] paths = pathInfo.split(DELIMITER);
        if(paths.length >= 2) {
            return new Path(paths[0], new QueryString(paths[1]));
        }
        return new Path(paths[0]);
    }

    private static void validate(String pathInfo) {
        if (!StringUtils.hasText(pathInfo))
            throw new StringEmptyException("pathinfo is empty");
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

    public String getPath() {
        return path;
//        if(!StringUtils.hasText(path))
//            return path;
//        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(getPath(), path1.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath());
    }
}
