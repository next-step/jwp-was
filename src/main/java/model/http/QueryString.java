package model.http;

import exception.IllegalHttpRequestException;

import java.util.Objects;

public class QueryString {
    private static final int INVALID_QUERY_STRING_LENGTH = 2;

    private QueryStringKey queryStringKey;
    private QueryStringValue queryStringValue;

    public QueryString(String queryString) {
        String[] queryStringData = queryString.split("=");
        if (queryStringData.length != INVALID_QUERY_STRING_LENGTH) {
            throw new IllegalHttpRequestException("Query String은 키, 값 한쌍으로 이루어져 있습니다.");
        }

        this.queryStringKey = new QueryStringKey(queryStringData[0]);
        this.queryStringValue = new QueryStringValue(queryStringData[1]);
    }

    public QueryString(QueryStringKey queryStringKey, QueryStringValue queryStringValue) {
        this.queryStringKey = queryStringKey;
        this.queryStringValue = queryStringValue;
    }

    public QueryStringKey getQueryStringKey() {
        return queryStringKey;
    }

    public QueryStringValue getQueryStringValue() {
        return queryStringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryString that = (QueryString) o;
        return Objects.equals(queryStringKey, that.queryStringKey) && Objects
                .equals(queryStringValue, that.queryStringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStringKey, queryStringValue);
    }
}