package webserver.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QueryString {
    private List<Map<String, String>> queryString;

    private static final String QUERY_STRING_SPLITTER = "&";
    private static final String KEY_VALUE_SPLITTER = "=";

    private QueryString(List<Map<String, String>> queryString) {
        this.queryString = queryString;
    }

    public static QueryString findQueryString(String queryString) {
        List<Map<String, String>> queryStrings = new ArrayList<>();
        String[] str = queryString.split(QUERY_STRING_SPLITTER);

        for (String s : str) {
            String[] values = s.split(KEY_VALUE_SPLITTER);
            queryStrings.add(Map.of(values[0], values[1]));
        }
        return new QueryString(queryStrings);
    }

    public List<Map<String, String>> getQueryString() {
        return queryString;
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
