package http.response;

import http.request.Cookie;
import http.request.Cookies;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private Map<String, String> header = new HashMap<>();

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public void addCookies(Cookies cookies) {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies.getCookies()) {
            sb.append(cookie.getFormat());
        }

        addHeader("Set-Cookie", sb.toString());
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

}
