package http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String header) {
        if (header == null || !header.startsWith("Cookie: ")) {
            throw new RuntimeException("쿠키 관련 헤더가 아님. header :: [" + header + "]");
        }

        String values = header.split(":", 2)[1].trim();
        for (String cookie : values.split(";")) {
            String[] c = cookie.split("=");
            if (c.length != 2) {
                throw new RuntimeException();
            }
            String cookieName = c[0].trim();
            String cookieValue = c[1].trim();
            cookies.put(cookieName, cookieValue);
        }
    }

    public String getValue(String name) {
        String defaultValue = "";
        return cookies.getOrDefault(name, defaultValue);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
