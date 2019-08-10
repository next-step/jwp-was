package webserver.http.request.support;

import webserver.http.request.ParameterMap;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class RequestParser {

    private static final String QUERY_SEPARATOR = "&";
    private static final String PARAMETER_SEPARATOR = "=";
    private static final String START_OF_QUERY = "?";

    public static ParameterMap parseQuery(String queryString) {
        ParameterMap parameterMap = new ParameterMap();
        String[] paramPairs = queryString.split(QUERY_SEPARATOR);
        for (String paramPair : paramPairs) {
            String[] tokens = paramPair.split(PARAMETER_SEPARATOR);
            if (tokens.length == 2 && !"".equals(tokens[0])) {
                parameterMap.put(tokens[0], tokens[1]);
            } else {
                throw new IllegalArgumentException("유효하지 않은 문자열입니다.");
            }
        }
        return parameterMap;
    }

    public static String parsePathFromRequestURI(String requestURI) {
        int queryIndex = requestURI.indexOf(START_OF_QUERY);
        if (queryIndex >= 0) {
            return requestURI.substring(0, queryIndex);
        }
        return requestURI;
    }

    public static ParameterMap parseQueryFromRequestURI(String requestURI) {
        int queryIndex = requestURI.indexOf(START_OF_QUERY);
        if (queryIndex >= 0) {
            return RequestParser.parseQuery(requestURI.substring(queryIndex + 1));
        }
        return new ParameterMap();
    }
}
