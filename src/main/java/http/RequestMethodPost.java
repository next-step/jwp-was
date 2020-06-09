package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMethodPost implements RequestMethod {
    private static final String METHOD_NAME = "POST";

    private final String path;
    private final RequestParameters requestParameters;

    public RequestMethodPost(final String path) {
        this(path, new RequestParameters(new HashMap<>()));
    }

    public RequestMethodPost(final String path, final RequestParameters requestParameters) {
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
        final RequestMethodPost that = (RequestMethodPost) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
