package webserver;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final int QUERY_KEY_INDEX = 0;
    private static final int QUERY_VALUE_INDEX = 1;
    private static final String QUERY_ELEMENT_DELIMITER = "&";
    private static final String QUERY_EQUAL_DELIMITER = "=";

    private Map<String, String> queryStrings;

    public QueryString(Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public static QueryString parse(String queryString) {
        validateQueryString(queryString);
        Map<String, String> queryStrings = new HashMap<>();
        String[] querys = queryString.split(QUERY_ELEMENT_DELIMITER);
        for (String query : querys) {
            setQueryString(queryStrings, query);
        }
        return new QueryString(queryStrings);
    }

    private static void validateQueryString(String queryString) {
        if (queryString == null) {
            throw new IllegalArgumentException("전달 받은 queryString null 일 수 없습니다.");
        }
    }

    private static void setQueryString(Map<String, String> queryStrings, String query) {
        String[] queryElements = query.split(QUERY_EQUAL_DELIMITER);
        queryStrings.put(queryElements[QUERY_KEY_INDEX], queryElements[QUERY_VALUE_INDEX]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryString that = (QueryString) o;

        return queryStrings != null ? queryStrings.equals(that.queryStrings) : that.queryStrings == null;
    }

    @Override
    public int hashCode() {
        return queryStrings != null ? queryStrings.hashCode() : 0;
    }
}
