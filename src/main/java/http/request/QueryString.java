package http.request;

import java.util.HashMap;
import java.util.Map;

public class QueryString {

    private Map<String, String> parameters;

    public QueryString(final Map<String, String> parameters) {
        this.parameters = parameters;
        if (this.parameters == null) this.parameters = new HashMap<>();
    }

    public String getParameter(final String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
