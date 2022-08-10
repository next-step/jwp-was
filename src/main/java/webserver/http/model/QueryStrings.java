package webserver.http.model;

import exception.IllegalHttpRequestException;

import java.util.*;

public class QueryStrings {
    private static final int INVALID_QUERY_STRING_LENGTH = 2;
    private Map<String, String> queryStringMap;

    public QueryStrings(String queryStrings) {
        if (queryStrings.isBlank()) {
            return;
        }

        queryStringMap = new LinkedHashMap<>();
        String[] queryStringArray = queryStrings.split("&");
        for (String queryString : queryStringArray) {
            initial(queryString);
        }
    }

    private void initial(String queryString) {
        String[] queryStringData = queryString.split("=");
        if (queryStringData.length != INVALID_QUERY_STRING_LENGTH) {
            throw new IllegalHttpRequestException("Query String은 키, 값 한쌍으로 이루어져 있습니다.");
        }
        queryStringMap.put(queryStringData[0], queryStringData[1]);
    }

    public Map<String, String> getQueryStringMap() {
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