package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class QueryString {
    public static String DELIMITER = "&";
    public static String QUERY_DELIMITER = "=";
    public static int QUERY_STRING_KEY_INDEX = 0;
    public static int QUERY_STRING_VALUE_INDEX = 1;

    private Map<String, Object> queryString;

    public QueryString() {
        this.queryString = new HashMap<>();
    }

    public QueryString(Map<String, Object> queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return !queryString.isEmpty() ? queryString.toString() : "";
    }

    public static QueryString parser(String parameter) {
        StringTokenizer stringTokenizer = new StringTokenizer(parameter, DELIMITER);
        Map<String, Object> map = new HashMap<>();

        while(stringTokenizer.hasMoreTokens()) {
            String[] token = stringTokenizer.nextToken().split(QUERY_DELIMITER);
            map.put(token[QUERY_STRING_KEY_INDEX], token[QUERY_STRING_VALUE_INDEX]);
        }

        return new QueryString(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryString);
    }
}
