package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestLine {
    private final Method method;
    private final String path;
    private final Protocol protocol;
    private final Map<String, String> queryStrings;


    public RequestLine(String method, String path, String protocolAndVersion) {
        this.method = Method.valueOf(method);
        this.path = path;
        this.protocol = new Protocol(protocolAndVersion);
        this.queryStrings = parseQueryStrings(path);
    }

    public static RequestLine of(String method, String path, String protocolAndVersion) {
        return new RequestLine(method, path, protocolAndVersion);
    }

    private Map<String, String> parseQueryStrings(final String path) {
        if ("".equals(path) || path == null) {
            return Collections.emptyMap();
        }

        if (!path.contains("?")) {
            return Collections.emptyMap();
        }

        final String[] pathValues = path.split("//?");
        final String queryStrings = pathValues[1];

        Map<String, String> keyAndValues = new HashMap<>();
        if (queryStrings.contains("&")) {
            final String[] values = queryStrings.split("=");
            return buildQueryStrings(keyAndValues, values);
        }

        final String[] keyAndValue = queryStrings.split("=");
        keyAndValues.put(keyAndValue[0], keyAndValue[1]);

        return keyAndValues;
    }

    private Map<String, String> buildQueryStrings(final Map<String, String> keyAndValues, final String[] values) {
        for (int i = 0; i < values.length; i++) {
            String[] keyAndValue = values[i].split("=");
            keyAndValues.put(keyAndValue[0], keyAndValue[1]);
        }
        return keyAndValues;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getPath(), that.getPath()) &&
                Objects.equals(getProtocol(), that.getProtocol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getPath(), getProtocol());
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }
}
