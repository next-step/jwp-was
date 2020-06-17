package http;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Map;

public class ResponseHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";

    private final Map<String, Object> headerMap;

    public ResponseHeaders() {
        this.headerMap = Maps.newHashMap();
    }

    @Nonnull
    public String makeHeadersString() {
        final StringBuilder sb = new StringBuilder();
        headerMap.entrySet().stream()
                .forEach(e -> {
                    sb.append(e.getKey()).append(": ").append(e.getValue()).append("\r\n");
                });

        return sb.toString();
    }

    @Nonnull
    public static ResponseHeaders makeEmptyResponseHeaders() {
        return new EmptyResponseHeaders();
    }

    static class EmptyResponseHeaders extends ResponseHeaders {
        private EmptyResponseHeaders() {
            super();
        }
    }

    public void put(String key, Object value) {
        headerMap.put(key, value);
    }

    public Object get(String key) {
        return headerMap.get(key);
    }
}
