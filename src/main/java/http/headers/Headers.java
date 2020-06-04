package http.headers;

import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    private Map<String, String> headers = new HashMap<>();

    public Headers(Reader reader) {

    }

    public Headers(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
