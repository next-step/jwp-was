package webserver.http;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    public static final String QUERY_DELIMETER = "&";
    public static final String KEY_VALUE_DELIMETER = "=";

    Map<String, String> parameters = new HashMap<>();

    private RequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static RequestBody parse(String body) {
        Map<String, String> parameters = new HashMap<>();
        if (!StringUtils.isBlank(body)) {
            parameters = createParams(body);
        }
        return new RequestBody(parameters);
    }

    private static Map<String, String> createParams(String queryString) {
        String[] splitedQuery = queryString.split(QUERY_DELIMETER);
        Map<String, String> queries = new HashMap<>();
        for (String query : splitedQuery) {
            String[] keyValue = query.split(KEY_VALUE_DELIMETER);
            queries.put(keyValue[0], keyValue[1]);
        }
        return queries;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
