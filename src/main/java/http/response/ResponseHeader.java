package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private final Map<String, String> header;

    public ResponseHeader(final Map<String, String> header) {
        this.header = header;
    }

    public ResponseHeader() {
        this.header = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String getHeaderValue(String key) {
        return this.header.getOrDefault(key, "");
    }

    public Map<String, String> getHeader() {
        return new HashMap<>(header);
    }
}
