package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private Map<String, String> header = new HashMap<>();

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public void addLocation(String redirectUrl) {
        this.header.put("Location", redirectUrl);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void addCookie(String cookie) {
        this.header.put("Set-Cookie", cookie);
    }
}
