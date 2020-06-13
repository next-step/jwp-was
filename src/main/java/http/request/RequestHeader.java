package http.request;

import java.util.List;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> header;
    private Cookies cookies;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
        this.cookies = new Cookies(header.getOrDefault("Cookie", ""));
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public List<Cookie> getCookies() {
        return cookies.getCookies();
    }

    public String getCookie(String key) {
        return cookies.getCookie(key);
    }

}
