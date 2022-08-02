package webserver;

import java.util.*;

public class HttpHeader {
    private static final String KEY_VALUE_DELIMITER = ": ";

    private final Map<String, String> headers;

    public HttpHeader() {
        this(new HashMap<>());
    }

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpHeader(List<String> headerLines) {
        if (headerLines.size() == 0) {
            this.headers = Collections.emptyMap();
            return;
        }

        Map<String, String> headers = new HashMap<>();

        headerLines.forEach(headerLine -> {
            String[] split = headerLine.split(KEY_VALUE_DELIMITER);
            if (split.length < 2) {
                return;
            }
            headers.put(split[0], split[1]);
        });

        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
}
