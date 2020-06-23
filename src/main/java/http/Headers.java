package http;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class Headers {
    private static final String HEADER_DELIMITER = ": ";
    final Map<String, String> headers = Maps.newHashMap();

    public void addHeader(final String header) {
        final String[] keyAndValue = header.trim().split(HEADER_DELIMITER);
        headers.put(keyAndValue[0], keyAndValue[1]);
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public String getHeader(final String key) {
        return headers.getOrDefault(key, null);
    }

    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }
}
