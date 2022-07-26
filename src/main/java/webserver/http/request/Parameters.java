package webserver.http.request;

import java.util.List;
import java.util.Map;

public class Parameters {
    private final Map<String, List<String>> keyValues;

    public Parameters(Map<String, List<String>> keyValues) {
        this.keyValues = keyValues;
    }

    @Override
    public String toString() {
        return "QueryParameters{" +
                "keyValues=" + keyValues +
                '}';
    }

    public void add(Parameters parameters) {
        keyValues.putAll(parameters.keyValues);
    }
}
