package http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getCookies() {
        Map<String, String> cookieResult = new HashMap<>();
        String value = header.getOrDefault("Cookie", "");
        String[] cookies = value.split(";");
        for (String cookie : cookies) {
            String[] result = cookie.split("=");
            addCookie(cookieResult, result);
        }
        return cookieResult;
    }

    private void addCookie(Map<String, String> cookieResult, String[] result) {
        if (result.length > 1) {
            cookieResult.put(result[0].trim(), result[1].trim());
        }
    }
}
