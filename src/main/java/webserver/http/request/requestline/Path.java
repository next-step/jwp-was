package webserver.http.request.requestline;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Path {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;
    private static final String QUERY_DELIMITER = "?";
    private static final String QUERY_REGEX_DELIMITER = "\\?";
    private static final String HTML_FILE_EXTENSION = ".html";
    private static final Set<String> STATIC_FILE_EXTENSION = Set.of(".css", ".eot", ".svg", "ttf", "woff", "woff2", ".png", ".js", "ico");

    private String path;
    private QueryString queryString;

    public Path(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public Path(String path) {
        this(path, new QueryString());
    }

    public static Path parse(String pathString) {
        validatePathString(pathString);

        if (pathString.contains(QUERY_DELIMITER)) {
            String[] pathElements = pathString.split(QUERY_REGEX_DELIMITER);
            return new Path(pathElements[PATH_INDEX], QueryString.parse(pathElements[QUERY_STRING_INDEX]));
        }

        return new Path(pathString, new QueryString(Collections.emptyMap()));
    }

    private static void validatePathString(String pathString) {
        if (pathString == null || pathString.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP RequestLine 의 path 는 비어있거나 null 일 수 없습니다.");
        }

        if (!pathString.startsWith("/")) {
            throw new IllegalArgumentException(String.format("요청된 HTTP RequestLine 의 path 는 '/'로 시작해야 합니다. 현재 입력된 path : %s", pathString));
        }
    }

    public boolean isHtmlFilePath() {
        if (this.path.endsWith(HTML_FILE_EXTENSION)) {
            return true;
        }
        return false;
    }

    public boolean isStaticFilePath() {
        return STATIC_FILE_EXTENSION.stream().anyMatch(extension -> this.path.endsWith(extension));

    }

    public boolean isPathEqual(Path path) {
        return this.path.equals(path.path);
    }

    public String getPath() {
        return this.path;
    }

    public String getQueryStringValue(String key) {
        return this.queryString.getValue(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path1 = (Path) o;

        if (!path.equals(path1.path)) return false;
        return Objects.equals(queryString, path1.queryString);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        return result;
    }
}
