package webserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private static final String DELIMITER_KEY = "&";
    private static final String DELIMITER_VALUE = "=";
    private static final int INDEX_KEY = 0;
    private static final int INDEX_VALUE = 1;

    private final Map<String, String> parameterMap;

    public QueryString(String parameter) {
        this.parameterMap = Arrays.stream(parameter.split(DELIMITER_KEY))
                .map(it -> it.split(DELIMITER_VALUE))
                .collect(Collectors.toMap(e -> e[INDEX_KEY], e -> e[INDEX_VALUE]));
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    @Override
    public String toString() {
        return "QueryString{parameterMap=" + parameterMap + '}';
    }
}
