package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private Map<String, String> header;

    public HttpHeaders(Map<String, String> header) {
        this.header = header;
    }

    public void put(String key, String value) {
        header.put(key, value.trim());
    }

    public String get(String key) {
        return header.get(key);
    }
}
