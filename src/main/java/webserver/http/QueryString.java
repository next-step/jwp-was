package webserver.http;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {
    private final static String PATH_SEPARATOR = "?";
    private final static String PARAMETER_SEPARATOR = "&";
    private final static String KEY_VALUE_SEPARATOR = "=";

    private Map<String, Object> parameterMap;

    private QueryString(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public static QueryString parse(String path) {
        Map<String, Object> parameterMap = getParameterStrings(path).stream()
                .map(QueryString::makeKeyValuePair)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return new QueryString(parameterMap);
    }

    private static List<String> getParameterStrings(String path) {
        String queryString = path.substring(path.indexOf(PATH_SEPARATOR) + 1);
        return Stream.of(queryString.split(PARAMETER_SEPARATOR))
                .filter(value -> value.contains(KEY_VALUE_SEPARATOR))
                .collect(Collectors.toList());
    }

    private static Pair<String, Object> makeKeyValuePair(String v) {
        String[] param = v.split(KEY_VALUE_SEPARATOR);
        return new Pair<>(param[0], param[1]);
    }

    public int parameterSize() {
        return parameterMap.size();
    }

    public Object get(String key) {
        return parameterMap.get(key);
    }
}
