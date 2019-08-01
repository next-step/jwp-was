package webserver.http;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {
    private Map<String, Object> parameterMap;

    private QueryString(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public static QueryString parse(String path) {
        String queryString = path.substring(path.indexOf("?") + 1);
        String[] values = queryString.split("&");
        Map<String, Object> parameterMap = Stream.of(values)
                .filter(value -> value.contains("="))
                .map(QueryString::makeKeyValuePair)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return new QueryString(parameterMap);
    }

    private static Pair<String, Object> makeKeyValuePair(String v) {
        String[] param = v.split("=");
        return new Pair<>(param[0], param[1]);
    }

    public int parameterSize() {
        return parameterMap.size();
    }

    public Object get(String key) {
        return parameterMap.get(key);
    }
}
