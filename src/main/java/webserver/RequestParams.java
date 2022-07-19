package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    private static final String QUERY_PARAM_PATH_SEPERATOR = "?";
    private static final String QUERY_PARAM_VALUES_SEPERATOR = "&";
    private static final String QUERY_PARAM_KEY_VALUE_SEPERATOR = "=";

    private Map<String, String> requestData = new HashMap<>();

    public RequestParams(String requestQueryParams) {
        int queryParamIndex = requestQueryParams.indexOf(QUERY_PARAM_PATH_SEPERATOR);

        if (queryParamIndex > -1) {
            parseRequestQuery(requestQueryParams.substring(queryParamIndex + 1));
        }
    }

    private void parseRequestQuery(String requestQueryParams) {
        String[] queryParam = requestQueryParams.split(QUERY_PARAM_VALUES_SEPERATOR);
        for (String parameter : queryParam) {
            String[] splitParameter = parameter.split(QUERY_PARAM_KEY_VALUE_SEPERATOR);
            requestData.put(splitParameter[0], splitParameter[1]);
        }
    }

    public Map<String, String> getRequestData() {
        return requestData;
    }
}
