package http;

import java.util.HashMap;
import java.util.Map;
import utils.HttpUtils;

public class Cookie {
    private final Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(String cookieLine){
        return new Cookie(HttpUtils.getPairs(cookieLine,";", "="));
    }

    public Map<String, String> getCookies() {
        return new HashMap<>(cookies);
    }
}
