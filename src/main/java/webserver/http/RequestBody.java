package webserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;

    private final String queryString;
    private final Map<String, String> parameters;

    public RequestBody(String queryString) {
        this.queryString = queryString;
        this.parameters = parseQueryString(queryString);
    }

    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();
        Arrays.stream(queryString.split(AMPERSAND)).forEach(parameter -> {
            String[] nameValuePair = parameter.split(EQUAL_SIGN);
            params.put(nameValuePair[INDEX_ZERO], nameValuePair[INDEX_ONE]);
        });
        return params;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }
}
