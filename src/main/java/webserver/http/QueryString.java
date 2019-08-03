package webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
public class QueryString {

    private static final String QUERY_SEPARATOR = "&";
    private static final String PARAMETER_SEPARATOR = "=";
    private Map<String, Object> parameterMap;

    public static QueryString parse(String requestURI) {

        Map<String, Object> parameterMap = new HashMap<>();
        int queryIndex = requestURI.indexOf("?");
        String query = requestURI.substring(queryIndex + 1);
        String[] parameters = query.split(QUERY_SEPARATOR);

        for (int i = 0; i < parameters.length; i++) {
            String[] parsedParameter = parameters[i].split(PARAMETER_SEPARATOR);
            if (parsedParameter.length == 2) {
                parameterMap.put(parsedParameter[0], parsedParameter[1]);
            } else if (parsedParameter.length == 1
                    && parameters[i].indexOf(PARAMETER_SEPARATOR) == parameters[i].length() - 1) {
                parameterMap.put(parsedParameter[0], "");
            } else {
                throw new IllegalArgumentException("유효하지 않은 문자열입니다.");
            }
        }

        return new QueryString(parameterMap);
    }


    public QueryString(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

}
