package http;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Map;

public class RequestHeaders {
    private final Map<String, String> headerMap;

    public RequestHeaders() {
        this.headerMap = Maps.newHashMap();
    }

    @Nonnull
    public static RequestHeaders makeEmptyRequestHeaders() {
        return new EmptyRequestHeaders();
    }

    static class EmptyRequestHeaders extends RequestHeaders {
        private EmptyRequestHeaders() {
            super();
        }
    }

    public void putWithValueTrim(@Nonnull String key, @Nonnull String value) {
        headerMap.put(key, value.trim());
    }

    public String get(@Nonnull String key) {
        return headerMap.get(key);
    }

    public String getOrDefault(@Nonnull String key, String defulat) {
        return headerMap.getOrDefault(key, defulat);
    }
}
