package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RequestPath {
    private static final Logger logger = LoggerFactory.getLogger(RequestPath.class);

    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final String BLANK = "";
    private static final String QUESTION_MARK = "?";
    private static final String ESCAPE_QUESTION_MARK = "\\?";

    private final String path;
    private final String query;

    public RequestPath(final String path) {
        logger.info("RequestPath init() path: {}, query: {}", path, BLANK);
        this.path = path;
        this.query = BLANK;
    }

    public RequestPath(final String path, final String query) {
        logger.info("RequestPath init() path: {}, query: {}", path, query);
        this.path = path;
        this.query = query;
    }

    public static RequestPath of(String path) {
        if ("".equals(path) || path == null) {
            throw new IllegalArgumentException();
        }
        if (!path.contains(QUESTION_MARK)) {
            return new RequestPath(path);
        }
        String[] tokens = path.split(ESCAPE_QUESTION_MARK);
        return new RequestPath(tokens[PATH_INDEX], tokens[QUERY_INDEX]);
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestPath that = (RequestPath) o;
        return Objects.equals(getPath(), that.getPath()) &&
                Objects.equals(getQuery(), that.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getQuery());
    }

    @Override
    public String toString() {
        return "RequestPath{" +
                "path='" + path + '\'' +
                ", queryString='" + query + '\'' +
                '}';
    }
}
