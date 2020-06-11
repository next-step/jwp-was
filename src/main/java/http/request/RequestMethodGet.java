package http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMethodGet implements RequestMethod {
    private static final String METHOD_NAME = "GET";

    private final String path;
    private final RequestParameters requestParameters;

    public RequestMethodGet(final String path) {
        this(path, new RequestParameters(""));
    }

    public RequestMethodGet(final String path, final RequestParameters requestParameters) {
        this.path = path;
        this.requestParameters = requestParameters;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public Map<String, String> getRequestParameters() {
        return new HashMap<>(requestParameters.getRequestParameters());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMethodGet that = (RequestMethodGet) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
