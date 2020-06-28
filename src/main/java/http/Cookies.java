package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cookies {
    private Map<String, String> cookies;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void put(String key, String value){
        cookies.put(key, value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public String makeCookie() {
        Set<String> keys =  cookies.keySet();
        String cookieString = "";
        for(String key: keys) {
            cookieString += String.format("Set-Cookie: %s=%s", key, cookies.get(key) + "\r\n");
        }
        return cookieString;
    }
}
