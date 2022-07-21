package model.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryStrings {
    private List<QueryString> queryStringList;

    public QueryStrings(String queryStrings) {
        if (queryStrings.isBlank()) {
            return;
        }

        queryStringList = new ArrayList<>();
        String[] queryStringArray = queryStrings.split("&");
        for (String queryString : queryStringArray) {
            queryStringList.add(new QueryString(queryString));
        }
    }

    public List<QueryString> getQueryStringList() {
        return queryStringList;
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
        return Objects.equals(queryStringList, that.queryStringList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStringList);
    }
}