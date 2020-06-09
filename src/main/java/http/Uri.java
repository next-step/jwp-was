package http;

import utils.RequestParseUtils;


import java.util.Objects;

public class Uri {

    private static final String QUERY_SIGN = "?";

    private final String path;
    private final QueryString queryString;

    private Uri(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Uri from(String fullUri) {
        String[] values = RequestParseUtils.splitIntoPair(fullUri, QUERY_SIGN);
        return new Uri(values[0], QueryString.from(values[1]));
    }

    public static Uri of(String path, String queryString) {
        return new Uri(path, QueryString.from(queryString));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uri uri = (Uri) o;
        return Objects.equals(path, uri.path) &&
                Objects.equals(queryString, uri.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }

    public String getPath() {
        return this.path;
    }
}
