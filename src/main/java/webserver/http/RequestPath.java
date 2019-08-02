package webserver.http;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class RequestPath {

    private static final Map<String, RequestPath> CACHE = new HashMap<>();

    private static final char PREFIX = '/';
    private static final int FIRST_INDEX = 0;

    private final String requestPath;

    private RequestPath(final String requestPath) {
        this.requestPath = requestPath;
    }

    static RequestPath of(final String rawRequestPath) {
        if (isInvalid(rawRequestPath)) {
            throw new InvalidRequestPathException(rawRequestPath);
        }

        return CACHE.computeIfAbsent(rawRequestPath, RequestPath::new);
    }

    String getRequestPath() {
        return requestPath;
    }

    private static boolean isInvalid(String rawRequestPath) {
        return StringUtils.isBlank(rawRequestPath) || rawRequestPath.charAt(FIRST_INDEX) != PREFIX;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestPath)) {
            return false;
        }

        final RequestPath that = (RequestPath) o;
        return Objects.equals(requestPath, that.requestPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestPath);
    }

    @Override
    public String toString() {
        return "RequestPath{" +
                "requestPath='" + requestPath + '\'' +
                '}';
    }
}
