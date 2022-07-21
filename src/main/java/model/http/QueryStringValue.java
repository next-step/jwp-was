package model.http;

import java.util.Objects;

public class QueryStringValue {
    private final String queryStringValue;

    public QueryStringValue(String queryStringValue) {
        this.queryStringValue = queryStringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryStringValue that = (QueryStringValue) o;
        return Objects.equals(queryStringValue, that.queryStringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStringValue);
    }
}