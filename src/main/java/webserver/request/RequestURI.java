package webserver.request;

import java.util.Map;

public class RequestURI {
    private static final String QUERY_STRING_DELIMITER = "\\?";

    private final String path;
    private final QueryString query;

    public RequestURI(String uri) {
        String[] queryString = uri.split(QUERY_STRING_DELIMITER);

        this.path = queryString[0];
        this.query = queryString.length == 2 ? new QueryString(queryString[1]) : null;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return this.query;
    }
}
