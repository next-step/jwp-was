package http;

import java.util.List;

public class QueryStrings {
    private final List<QueryString> queryStrings;

    public QueryStrings(final List<QueryString> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public List<QueryString> getQueryStrings() {
        return queryStrings;
    }
}
