package webserver.http;

import utils.CastingUtils;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class HttpPath {
    public static final String VALIDATION_MESSAGE = "형식에 맞지 않습니다.";
    private static final String PATH_DELIMITER = "\\?";
    private static final int CORRECT_LENGTH = 2;
    private static final Pattern QUERY_STRING_PATTERN = Pattern.compile("&?([^=&]+)=([^=&]+)");

    private final String path;
    private final Map<String, Object> queryStrings;

    public HttpPath(String pathSpec) {
        final String[] splitPathSpec = pathSpec.split(PATH_DELIMITER);
        this.path = splitPathSpec[0];
        this.queryStrings = makeQueryStrings(splitPathSpec);
    }

    public HttpPath(String path, Map<String, Object> queryStrings) {
        this.path = path;
        this.queryStrings = queryStrings;
    }

    private Map<String, Object> makeQueryStrings(String[] splitPathSpec) {
        if (splitPathSpec.length == CORRECT_LENGTH) {
            return HttpUtils.parseParameters(splitPathSpec[1], QUERY_STRING_PATTERN);
        }
        return new HashMap<>();
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

    public Map<String, Object> getQueryStrings() {
        return queryStrings;
    }

    public <T> T getQueryString(String key, Class<T> returnType) {
        return CastingUtils.cast(queryStrings.get(key), returnType);
    }

    public String getQueryString(String key) {
        return getQueryString(key, String.class);
    }
}
