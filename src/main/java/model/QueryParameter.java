package model;

import java.util.Map;

public class QueryParameter {

    private final Map<String, String> parameters;

    public QueryParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

}
