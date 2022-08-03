package webserver.request.domain.request;

import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

import java.util.Objects;

public class Path {

    private static final String DELIMITER = "\\?";

    private String path;
    // QueryString = RequestBody
    private RequestBody queryString = null;

    public Path(String path) {
        this.path = path;
    }

    public Path(String path, RequestBody queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path parse(String pathInfo) {
        validate(pathInfo);

        String[] path = pathInfo.split(DELIMITER);
        if(path.length >= 2) {
            return new Path(path[0], new RequestBody(path[1]));
        }
        return new Path(path[0]);
    }

    private static void validate(String pathInfo) {
        if (!StringUtils.hasText(pathInfo))
            throw new StringEmptyException("pathinfo is empty");
    }

    public RequestBody getQueryString() {
        return this.queryString;
    }

    public String getPath() {
        return path;
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
