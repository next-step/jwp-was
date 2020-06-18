package cookie;

import java.util.HashMap;
import java.util.Map;

public class Cookies {

    private final Map<String, String> cookieMap;

    public Cookies() {
        cookieMap = new HashMap<>();
    }

    public Cookies(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap != null ? cookieMap : new HashMap<>();
    }

    public String getCookie(String key) {
        return cookieMap.get(key);
    }
}
