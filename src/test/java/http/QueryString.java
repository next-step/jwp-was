package http;

import java.util.Map;

public class QueryString {

    private final Map<String, String> parameters;

    public QueryString(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}