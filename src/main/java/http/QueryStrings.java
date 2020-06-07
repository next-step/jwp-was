package http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QueryStrings {
    private List<QueryString> queryStrings = new ArrayList();

    public QueryStrings(List<QueryString> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public QueryStrings(String queryStringLine) {
        String[] queryStringElements = queryStringLine.split("&");
        Arrays.stream(queryStringElements)
                .forEach(queryString -> {
                    String[] values = queryString.split("=");
                    if(values.length != 2) {
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열 입니다.");
                    }
                    queryStrings.add(new QueryString(values[0], values[1]));
                });
    }

    public String getValue(String key) {
        return queryStrings.stream()
                .filter(queryString -> queryString.exists(key))
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryStrings that = (QueryStrings) o;
        return Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStrings);
    }
}
