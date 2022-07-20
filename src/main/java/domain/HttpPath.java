package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpPath {
    private final String path;
    private final List<Map<String, String>> queryStrings = new ArrayList<>();

    public HttpPath(String pathSpec) {
        final String[] splitPathSpec = pathSpec.split("\\?");
        this.path = splitPathSpec[0];
        if (splitPathSpec.length == 2) {
            setQueryStrings(splitPathSpec[1]);
        }
    }

    private void setQueryStrings(String queryStringSpec) {
        final String[] splitQueryStringSpecs = queryStringSpec.split("&");
        for (String splitQueryStringSpec : splitQueryStringSpecs) {
            addQueryString(splitQueryStringSpec);
        }
    }

    private void addQueryString(String splitQueryStringSpec) {
        final String[] items = splitQueryStringSpec.split("=");
        if (items.length == 2) {
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
