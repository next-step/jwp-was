package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private Map<String, String> header = new HashMap<>();

    public void putHeader(String key, String value) {
        this.header.put(key, value);
    }

    public void putContentType() {
        this.header.put("Content-Type", "text/html;charset=utf-8");
    }

    public void putContentLength(int length) {
        this.header.put("Content-Length", String.valueOf(length));
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
