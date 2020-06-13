package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import utils.Args;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
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
        if (Strings.isEmpty(queryString)) {
            return EMPTY_QUERY_STRING;
        }
        return new QueryString(URLDecoder.decode(queryString));
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
            String[] values = keyValue.split(KEY_VALUE_DELIMITER, -1);
            Args.check(values.length == 2, ILLEGAL_QUERY);
            queryMap.put(values[0].trim(), values[1].trim());
        }
        return queryMap;
    }

    public String getParameter(String key) {
        return queryMap.get(key);
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
        if (queryString != null ? !queryString.equals(that.queryString) : that.queryString != null) {
            return false;
        }
        return queryMap != null ? queryMap.equals(that.queryMap) : that.queryMap == null;
    }

    @Override
    public int hashCode() {
        int result = queryString != null ? queryString.hashCode() : 0;
        result = 31 * result + (queryMap != null ? queryMap.hashCode() : 0);
        return result;
    }
}
