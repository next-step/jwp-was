package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParam {
    private static final String QUERY_PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public static final QueryParam EMPTY_QUERY_PARAM = new QueryParam(Collections.emptyMap());

    private Map<String, String> queryParams;

    private QueryParam(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public static QueryParam parse(String queryString) {
        Map<String, String> queryParams = Arrays.stream(queryString.split(QUERY_PARAM_SEPARATOR))
                .map(QueryParam::getKeyValue)
                .filter(QueryParam::isNotEmpty)
                .filter(QueryParam::isPair)
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1], (value1, value2) -> value1));
        return new QueryParam(queryParams);
    }

    public String getParameter(String key) {
        return queryParams.get(key);
    }

    public Map<String, String> getParameterMap() {
        return queryParams;
    }

    private static String[] getKeyValue(String queryString) {
        return queryString.split(KEY_VALUE_SEPARATOR);
    }

    private static boolean hasQueryParams(String[] split) {
        return split.length > 1;
    }

    private static boolean isNotEmpty(String[] target) {
        return target.length != 0;
    }

    private static boolean isPair(String[] keyValue) {
        return keyValue.length == 2;
    }
}
