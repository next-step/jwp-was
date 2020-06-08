package http;

import java.util.Map;

public class QueryString {

    private final Map<String, String> parameters;

    public QueryString(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(final String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
