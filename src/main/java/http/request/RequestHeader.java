package http.request;

import java.util.Map;

public class RequestHeader {
    private Map<String, String> header;
    private Cookies cookies;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
        this.cookies = new Cookies(header.getOrDefault("Cookie", ""));
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getCookies() {
        return cookies.getCookies();
    }

}
