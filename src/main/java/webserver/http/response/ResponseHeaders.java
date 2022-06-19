package webserver.http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private final Map<String, String> headers = new HashMap<>();

    public void add(final String name, final String value) {
        this.headers.put(name, value);
    }

    public Map<String, String> getAll() {
        return new HashMap<>(this.headers);
    }
}
