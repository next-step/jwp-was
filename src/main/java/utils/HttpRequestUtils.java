package utils;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    private static final String QUERY_PARAM_VALUES_SEPERATOR = "&";
    private static final String QUERY_PARAM_KEY_VALUE_SEPERATOR = "=";

    public static Map<String, String> queryStringParser(String queryString) {
        HashMap<String, String> requestData = new HashMap<>();

        if (queryString == null || queryString.isEmpty()) {
            return requestData;
        }

        String[] queryParam = split(queryString, QUERY_PARAM_VALUES_SEPERATOR);

        for (String parameter : queryParam) {
            parameterParser(requestData, parameter);
        }
        return requestData;
    }

    private static void parameterParser(Map<String, String> requestData, String parameter) {
        String[] splitParameter = split(parameter, QUERY_PARAM_KEY_VALUE_SEPERATOR);

        if (splitParameter.length != 2) {
            return;
        }
        requestData.put(splitParameter[0], splitParameter[1]);
    }

    public static String[] split(String input, String delimeter) {
        return input.split(delimeter);
    }
}
