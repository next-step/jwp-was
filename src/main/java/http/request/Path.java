package http.request;

import lombok.Getter;
import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
public class Path {
    static final String QUERY_STRING_SPLITTER = "\\?";
    static final String PARAMETER_SPLITTER = "&";
    static final String KEY_VALUE_SPLITTER = "=";

    private final String path;
    private final Map<String, String> queryString;

    public static Path of(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }

        String [] split = path.split(QUERY_STRING_SPLITTER);

        if (split.length == 1) {
            return new Path(
                split[0],
                Collections.emptyMap()
            );
        }

        return new Path(
            split[0],
            buildQueryString(split[1])
        );
    }

    private static Map<String, String> buildQueryString(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(collectQueryStringMap(queryString));
    }

    private static Map<String, String> collectQueryStringMap(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_SPLITTER))
            .filter(param -> param.contains(KEY_VALUE_SPLITTER))
            .map(param -> param.split(KEY_VALUE_SPLITTER))
            .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    public Path(String path, Map<String, String> queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("path는 ").append(path).append("\r\n");

        return sb.toString();
    }

}
