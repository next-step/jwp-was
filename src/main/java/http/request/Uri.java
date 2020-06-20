package http.request;


import utils.StringUtils;

import java.util.Objects;

public class Uri {

    private static final String QUERY_SIGN = "?";
    public static final String REQUEST_URI_IS_INVALID = "request uri is invalid.";

    private final String path;
    private final QueryString queryString;

    private Uri(String path, QueryString queryString) {
        if (path == null || !path.startsWith("/")) {
            throw new IllegalArgumentException(REQUEST_URI_IS_INVALID);
        }
        this.path = path;
        this.queryString = queryString;
    }

    public static Uri from(String fullUri) {
        String[] values = StringUtils.splitIntoPair(fullUri, QUERY_SIGN);
        return new Uri(values[0], new QueryString(values[1]));
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

    public String getExtension() {
        String extension = "";
        int indexOfComma = this.path.lastIndexOf(".");
        if (indexOfComma > 0) {
            extension = this.path.substring(indexOfComma + 1);
        }
        return extension;
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
}
