package webserver.request;

import java.util.*;

public class Header {
    private static final String KEY_VALUE_DELIMITER = ": ";

    private final Map<String, String> headers;

    public Header(Map<String, String> headers) {
        this.headers = headers;
    }

    public Header(List<String> headerLines) {
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

    Map<String, String> getHeaders() {
        return headers;
    }
}
