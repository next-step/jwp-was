package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpPath {
    private static final String PATH_DELIMITER = "\\?";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String QUERY_STRING_ITEM_DELIMITER = "=";
    private static final int PAIR_LENGTH = 2;

    private final String path;
    private final List<Map<String, String>> queryStrings = new ArrayList<>();

    public HttpPath(String pathSpec) {
        final String[] splitPathSpec = pathSpec.split(PATH_DELIMITER);
        this.path = splitPathSpec[0];
        if (splitPathSpec.length == PAIR_LENGTH) {
            setQueryStrings(splitPathSpec[1]);
        }
    }

    private void setQueryStrings(String queryStringSpec) {
        final String[] splitQueryStringSpecs = queryStringSpec.split(QUERY_STRING_DELIMITER);
        for (String splitQueryStringSpec : splitQueryStringSpecs) {
            addQueryString(splitQueryStringSpec);
        }
    }

    private void addQueryString(String splitQueryStringSpec) {
        final String[] items = splitQueryStringSpec.split(QUERY_STRING_ITEM_DELIMITER);
        if (items.length == PAIR_LENGTH) {
            queryStrings.add(Map.of(items[0], items[1]));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpPath httpPath = (HttpPath) o;
        return Objects.equals(path, httpPath.path) && Objects.equals(queryStrings, httpPath.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryStrings);
    }
}
