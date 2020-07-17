package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Cookie {
    private Map<String, String> cookies;

    private static final String MAX_AGE = "Max-Age";
    private static final String DOMAIN = "Domain";
    private static final String PATH = "Path";

    public Cookie() {
        this.cookies = new HashMap<>();
    }

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void put(String key, String value) {
        cookies.put(key, value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public Set<String> keySet() {
        return this.cookies.keySet();
    }

    public void setPath(String path) {
        put(PATH, path);
    }

    public void setMaxAge(Integer maxAge) {
        put(MAX_AGE, maxAge.toString());
    }

    public void setDomain(String domain) {
        put(DOMAIN, domain);
    }

    /* public String makeCookie() {
        Set<String> keys =  cookies.keySet();
        String cookieString = "";
        for(String key: keys) {
            cookieString += String.format("Set-Cookie: %s=%s", key, cookies.get(key) + "\r\n");
        }
        return cookieString;
    }*/
}
