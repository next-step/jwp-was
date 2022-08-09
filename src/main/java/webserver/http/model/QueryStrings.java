package webserver.http.model;

import java.util.*;

public class QueryStrings {
    private Map<QueryStringKey, QueryStringValue> queryStringMap;

    public QueryStrings(String queryStrings) {
        if (queryStrings.isBlank()) {
            return;
        }

        queryStringMap = new LinkedHashMap<>();
        String[] queryStringArray = queryStrings.split("&");
        for (String queryString : queryStringArray) {
            QueryString queryStringObject = new QueryString(queryString);
            queryStringMap.put(queryStringObject.getQueryStringKey(), queryStringObject.getQueryStringValue());
        }
    }

    public String queryStringValue(String queryStringKey) {
        return queryStringMap.get(new QueryStringKey(queryStringKey)).getQueryStringValue();
    }

    public Map<QueryStringKey, QueryStringValue> getQueryStringMap() {
        return queryStringMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryStrings that = (QueryStrings) o;
        return Objects.equals(queryStringMap, that.queryStringMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStringMap);
    }
}