package domain;

import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpPath {
    public static final String VALIDATION_MESSAGE = "경로가 형식에 맞지 않습니다.";
    private static final String PATH_DELIMITER = "\\?";
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String QUERY_STRING_ITEM_DELIMITER = "=";
    private static final int CORRECT_LENGTH = 2;

    private final String path;
    private final Map<String, String> queryStrings;

    public HttpPath(String pathSpec) {
        final String[] splitPathSpec = pathSpec.split(PATH_DELIMITER);
        validatePathSpec(splitPathSpec);

        this.path = splitPathSpec[0];
        this.queryStrings = makeQueryStrings(splitPathSpec);
    }

    public HttpPath(String path, Map<String, String> queryStrings) {
        this.path = path;
        this.queryStrings = queryStrings;
    }

    private void validatePathSpec(String[] splitPathSpec) {
        if (splitPathSpec.length > CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private Map<String, String> makeQueryStrings(String[] splitPathSpec) {
        if (splitPathSpec.length == CORRECT_LENGTH) {
            return makeQueryStrings(splitPathSpec[1]);
        }
        return new HashMap<>();
    }

    private Map<String, String> makeQueryStrings(String queryStringSpec) {
        final Map<String, String> queryStrings = new HashMap<>();
        final String[] splitQueryStringSpecs = queryStringSpec.split(QUERY_STRING_DELIMITER);
        for (String splitQueryStringSpec : splitQueryStringSpecs) {
            addQueryString(queryStrings, splitQueryStringSpec);
        }
        return queryStrings;
    }

    private void addQueryString(Map<String, String> queryStrings, String splitQueryStringSpec) {
        final String[] querystringItems = splitQueryStringSpec.split(QUERY_STRING_ITEM_DELIMITER);
        validateQueryStringItems(querystringItems);
        queryStrings.put(querystringItems[0], HttpUtils.decode(querystringItems[1]));
    }

    private void validateQueryStringItems(String[] items) {
        if (items.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
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
        return "HttpPath{" +
                "path='" + path + '\'' +
                ", queryStrings=" + queryStrings +
                '}';
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }
}
