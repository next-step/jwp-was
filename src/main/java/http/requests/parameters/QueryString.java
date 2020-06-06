package http.requests.parameters;

import java.util.Map;
import java.util.Objects;

public class QueryString {

    private final Map<String, String> map;

    public QueryString(Map<String, String> parsedQueryString) {
        this.map = parsedQueryString;
    }

    public String getParameter(String attributeName) {
        return map.get(attributeName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
