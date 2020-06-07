package http;

import java.util.HashMap;
import java.util.Objects;

public class RequestMethodGet implements RequestMethod {
    private static final String METHOD_NAME = "GET";

    private final String path;
    private final QueryStrings queryStrings;

    public RequestMethodGet(final String path) {
        this(path, new QueryStrings(new HashMap<>()));
    }

    public RequestMethodGet(final String path, final QueryStrings queryStrings) {
        this.path = path;
        this.queryStrings = queryStrings;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    public QueryStrings getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMethodGet that = (RequestMethodGet) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
