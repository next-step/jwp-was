package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private Map<String, String> header = new HashMap<>();

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

}
