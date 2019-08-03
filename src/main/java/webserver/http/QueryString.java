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
    private static final String EMPTY_VALUE = "";

    private final Map<String, Object> parameterMap;

    public static QueryString parse(String requestURI) {

        Map<String, Object> parameterMap = new HashMap<>();
        int queryIndex = requestURI.indexOf("?");
        String query = requestURI.substring(queryIndex + 1);
        String[] pairString = query.split(QUERY_SEPARATOR);

        for (int i = 0; i < pairString.length; i++) {
            // Todo: if 문 없애기
            // Todo: 같은 필드명이 들어오면 배열로 처리?
            String[] pair = pairString[i].split(PARAMETER_SEPARATOR);
            if (pair.length == 2 && !"".equals(pair[0])) {
                parameterMap.put(pair[0], pair[1]);
            } else if (pair.length == 1
                    && pairString[i].indexOf(PARAMETER_SEPARATOR) == pairString[i].length() - 1) {
                parameterMap.put(pair[0], EMPTY_VALUE);
            } else {
                throw new IllegalArgumentException("유효하지 않은 문자열입니다.");
            }
        }

        return new QueryString(parameterMap);
    }

    private QueryString(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

}
