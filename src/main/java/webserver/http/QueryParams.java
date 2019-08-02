package webserver.http;

import java.util.*;
import java.util.regex.Pattern;

public class QueryParams {

    private final static Pattern QUERY_PARAM_PATTERN = Pattern.compile("^[^=\\s]+(=(\\S+)?)?");

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

        String[] keyAndValue = queryKeyValue.split("=", 2);

        if(keyAndValue.length != 2) {
            return;
        }

        String key = keyAndValue[0];
        String value = keyAndValue[1];
        this.parameterMap.computeIfAbsent(key, (k) -> new ArrayList<>()).add(value);
    }

    public static QueryParams parseByPath(String path) {

        String[] queryParamKeyVaues = path.replaceAll("^.*\\?", "")
                .split("&");

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
