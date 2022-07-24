package webserver.http;

import java.util.Map;
import java.util.Objects;

public class Path {
    private static final String PATH_DELIMITER = "?";
    private static final String PATH_SPLIT_DELIMITER = "\\?";

    private final String path;
    private final RequestParams requestParams;

    public Path(String path, RequestParams requestParams) {
        this.path = path;
        this.requestParams = requestParams;
    }

    public static Path create(String fullPath) {

        if (fullPath.contains(PATH_DELIMITER)) {
            String[] splitPath = fullPath.split(PATH_SPLIT_DELIMITER);
            return new Path(splitPath[0], new RequestParams(splitPath[1]));
        }

        return new Path(fullPath, new RequestParams());
    }

    public String path() {
        return path;
    }

    public Map<String, String> requestParams() {
        return requestParams.params();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) && Objects.equals(requestParams, path1.requestParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, requestParams);
    }
}
