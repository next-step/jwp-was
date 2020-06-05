package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import utils.Args;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class QueryString {

    protected static final String ILLEGAL_QUERY = "유효하지 않은 Query 입니다.";
    private static final String QUERY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final QueryString EMPTY_QUERY_STRING = new QueryString("");

    private final String queryString;
    private final Map<String, String> queryMap;

    private QueryString(String queryString) {
        this.queryString = queryString;
        this.queryMap = parseQuery(queryString);
    }

    public static QueryString of(String queryString) {
        if (queryString == null) {
            return EMPTY_QUERY_STRING;
        }
        return new QueryString(queryString);
    }

    public static QueryString ofNull() {
        return QueryString.of(null);
    }

    private Map<String, String> parseQuery(String queryString) {
        if (queryString.equals("")) {
            return Collections.emptyMap();
        }
        Map<String, String> queryMap = new HashMap<>();
        for (String keyValue : queryString.split(QUERY_DELIMITER)) {
            String[] values = keyValue.split(KEY_VALUE_DELIMITER);
            Args.check(values.length == 2, ILLEGAL_QUERY);
            queryMap.put(values[0], values[1]);
        }
        return queryMap;
    }

    public String getPrameter(String userId) {
        return queryMap.get(userId);
    }

    @Override
    public String toString() {
        return queryString;
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

        return queryMap != null ? queryMap.equals(that.queryMap) : that.queryMap == null;
    }

    @Override
    public int hashCode() {
        return queryMap != null ? queryMap.hashCode() : 0;
    }
}
