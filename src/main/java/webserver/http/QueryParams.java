package webserver.http;

import java.util.*;
import java.util.regex.Pattern;

public class QueryParams {

    private static final int QUERY_KEY_VALUE_SPLIT_LIMIT = 2;

    private static final String QUERY_KEY_VALUE_SPLIT_SIGN = "=";
    
    private static final String PATH_QUERY_STRING_IGNORE_REGEX = "^.*\\?";

    private static final String QUERY_STRING_SPLIT_SIGN = "&";

    private static final String EMPTY_STRING = "";

    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("^[^=\\s]+(=(\\S+)?)?");

    private final Map<String, List<String>> parameterMap;

    private QueryParams(String[] queryParamKeyVaues){
        this.parameterMap = new HashMap<>();

        for(String queryKeyValue : queryParamKeyVaues) {
            setQueryParam(this.parameterMap, queryKeyValue);
        }
    }

    private void setQueryParam(Map<String, List<String>> parameterMap, String queryKeyValue) {

        if(!QUERY_PARAM_PATTERN.matcher(queryKeyValue).find()) {
            throw new IllegalArgumentException("queryString 형식이아닙니다.");
        }

        String[] keyAndValue = queryKeyValue.split(QUERY_KEY_VALUE_SPLIT_SIGN, QUERY_KEY_VALUE_SPLIT_LIMIT);

        if(keyAndValue.length != QUERY_KEY_VALUE_SPLIT_LIMIT) {
            return;
        }

        String key = keyAndValue[0];
        String value = keyAndValue[1];
        this.parameterMap.computeIfAbsent(key, (k) -> new ArrayList<>()).add(value);
    }

    public static QueryParams parseByPath(String path) {

        String[] queryParamKeyVaues = path.replaceAll(PATH_QUERY_STRING_IGNORE_REGEX, EMPTY_STRING)
                .split(QUERY_STRING_SPLIT_SIGN);

        return new QueryParams(queryParamKeyVaues);
    };


    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
                .filter(values -> values.length > 0)
                .map(values -> values[0])
                .orElse(null);
    }

    public String[] getParameterValues(String name) {
        return Optional.ofNullable(this.parameterMap.get(name))
                .map(valueList -> valueList.toArray(new String[valueList.size()]))
                .orElse(null);
    }

}
