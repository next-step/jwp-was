package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryStrings {
    private Map<String, String> queryStrings;

    public QueryStrings(final Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public Map<String, String> getQueryStrings() {
        return new HashMap<>(queryStrings);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryStrings that = (QueryStrings) o;
        return Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryStrings);
    }
}
