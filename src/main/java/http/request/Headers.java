package http.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Headers {
    private Map<String, String> headers = new HashMap<>();

    public Headers(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public String getParameter(String key) {
        return this.headers.get(key);
    }

    public int getSize() {
        return this.headers.size();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Set<String> getKeySet() {
        return headers.keySet();
    }
}
