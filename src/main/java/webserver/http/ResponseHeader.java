package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseHeader {

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }
}
