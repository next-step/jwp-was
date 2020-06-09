package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParameters {
    private Map<String, String> requestParameters;

    public RequestParameters(final Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Map<String, String> getRequestParameters() {
        return new HashMap<>(requestParameters);
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
