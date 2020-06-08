package http.request;


public class QueryString {
    private final String queryString;

    public QueryString(final String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }
}
