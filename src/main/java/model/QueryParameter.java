package model;

import java.util.Map;

public class QueryParameter {

    private Map<String, String> parameters;

    public QueryParameter(Map<String, String> parameters) {
        if (!parameters.isEmpty()) {
            this.parameters = parameters;
        }
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

}
