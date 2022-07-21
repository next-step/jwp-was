package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestParamMap {

    private static final int QUERY_STRING_INDEX = 1;
    private static final int PARAMETER_KEY_INDEX = 0;
    private static final int PARAMETER_VALUE_INDEX = 1;

    private static final String QUERY_STRING_START_CHARACTER = "?";
    private static final String REQUEST_URL_PATH_DELIMITER = "\\?";
    private static final String REQUEST_URL_PARAMETER_DELIMITER = "&";
    private static final String REQUEST_URL_KEY_VALUE_DELIMITER = "=";

    private Map<String, String> requestParamMap = new HashMap<>();

    protected RequestParamMap() {

    }

    private RequestParamMap(final Map<String, String> requestParamMap) {
        this.requestParamMap = requestParamMap;
    }

    public static RequestParamMap from(final String requestUrl) {
        return new RequestParamMap(parseQueryString(requestUrl));
    }

    private static Map<String, String> parseQueryString(final String requestUrl) {
        if (requestUrl.contains(QUERY_STRING_START_CHARACTER)) {
            String queryString = requestUrl.split(REQUEST_URL_PATH_DELIMITER)[QUERY_STRING_INDEX];
            String[] splitQueryString = queryString.split(REQUEST_URL_PARAMETER_DELIMITER);

            return getRequestParamMap(splitQueryString);
        }

        return new HashMap<>();
    }

    private static Map<String, String> getRequestParamMap(String[] splitQueryString) {
        Map<String, String> requestParamMap = new HashMap<>();
        
        for (String parameter : splitQueryString) {
            String[] splitParameter = parameter.split(REQUEST_URL_KEY_VALUE_DELIMITER);
            requestParamMap.put(splitParameter[PARAMETER_KEY_INDEX], splitParameter[PARAMETER_VALUE_INDEX]);
        }

        return requestParamMap;
    }

    private static boolean existQueryString(String requestUrl) {
        return requestUrl.contains(QUERY_STRING_START_CHARACTER);
    }

    private static String containQueryStringStartCharacter(String requestUrl) {
        if (requestUrl.contains(QUERY_STRING_START_CHARACTER)) {
            return requestUrl.split(REQUEST_URL_PATH_DELIMITER)[QUERY_STRING_INDEX];
        }

        return requestUrl;
    }

    public String get(String key) {
        return this.requestParamMap.get(key);
    }
}
