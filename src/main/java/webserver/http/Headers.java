package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    private Map<String, String> headers;

    public Headers() {
        this(new HashMap<>());
    }

    public Headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void putHeader(String line) {
        String[] tokens = line.split(":");
        headers.put(tokens[0].trim(), tokens[1].trim());
    }
}
