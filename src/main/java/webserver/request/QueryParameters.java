package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class QueryParameters {
    private final Map<String, String> parameters = new HashMap<>();

    public QueryParameters(final String queryString) {
        if (queryString.isEmpty()) {
            return;
        }

        final String[] keyAndValues = queryString.split("&");

        for (final String keyAndValueString : keyAndValues) {
            final String[] keyAndValue = keyAndValueString.split("=");
            this.parameters.put(keyAndValue[0], keyAndValue[1]);
        }
    }

    public Map<String, String> getParameters() {
        return new HashMap<>(this.parameters);
    }

    public String getParameterOrNull(final String key) {
        return this.parameters.get(key);
    }
}
