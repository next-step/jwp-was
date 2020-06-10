package http;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParameters {

    private static final String SEPARATOR_QUERY_STRING = "&";
    private static final String SEPARATOR_KEY_VALUE = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> requestParameters;

    public RequestParameters(final String queryString) {
        if (Strings.isNotBlank(queryString)) {
            this.requestParameters = create(queryString);
        }
    }

    public Map<String, String> getRequestParameters() {
        return new HashMap<>(requestParameters);
    }

    private Map<String, String> create(String queryString) {
        Map<String, String> requestParameters = new HashMap<>();
        String[] queries = queryString.split(SEPARATOR_QUERY_STRING);
        for (final String query : queries) {
            String[] keyAndValues = query.split(SEPARATOR_KEY_VALUE);
            requestParameters.put(keyAndValues[KEY_INDEX], keyAndValues[VALUE_INDEX]);
        }
        return requestParameters;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestParameters that = (RequestParameters) o;
        return Objects.equals(requestParameters, that.requestParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestParameters);
    }
}
