package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpPath {
    public static final String VALIDATION_MESSAGE = "경로가 형식에 맞지 않습니다.";
    private static final String PATH_DELIMITER = "\\?";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String QUERY_STRING_ITEM_DELIMITER = "=";
    private static final int CORRECT_LENGTH = 2;

    private final String path;
    private final List<Map<String, String>> queryStrings = new ArrayList<>();

    public HttpPath(String pathSpec) {
        final String[] splitPathSpec = pathSpec.split(PATH_DELIMITER);
        validatePathSpec(splitPathSpec);

        this.path = splitPathSpec[0];
        if (splitPathSpec.length == CORRECT_LENGTH) {
            setQueryStrings(splitPathSpec[1]);
        }
    }

    private void validatePathSpec(String[] splitPathSpec) {
        if (splitPathSpec.length > CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void setQueryStrings(String queryStringSpec) {
        final String[] splitQueryStringSpecs = queryStringSpec.split(QUERY_STRING_DELIMITER);
        for (String splitQueryStringSpec : splitQueryStringSpecs) {
            addQueryString(splitQueryStringSpec);
        }
    }

    private void addQueryString(String splitQueryStringSpec) {
        final String[] querystringItems = splitQueryStringSpec.split(QUERY_STRING_ITEM_DELIMITER);
        validateQueryStringItems(querystringItems);
        queryStrings.add(Map.of(querystringItems[0], querystringItems[1]));
    }

    private void validateQueryStringItems(String[] items) {
        if (items.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException("경로가 형식에 맞지 않습니다.");
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

    @Override
    public String toString() {
        return path;
    }
}
