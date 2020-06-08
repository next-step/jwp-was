package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private final Map<String, String> headers = new HashMap<>();

    public String get(String headerKey) {
        return headers.get(headerKey);
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }
}
