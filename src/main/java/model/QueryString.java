package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class QueryString {
    public static String DELIMITER = "&";
    public static String QUERY_DELIMITER = "=";

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

    public Object getParameter(String key) {
        return queryString.get(key);
    }

    public static QueryString parser(String parameter) {
        StringTokenizer stringTokenizer = new StringTokenizer(parameter, DELIMITER);
        Map<String, Object> map = new HashMap<>();

        while(stringTokenizer.hasMoreTokens()) {
            String[] token = stringTokenizer.nextToken().split(QUERY_DELIMITER);
            map.put(token[0], token[1]);
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
