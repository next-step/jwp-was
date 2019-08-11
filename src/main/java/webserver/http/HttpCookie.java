package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private Map<String, String> cookies;
    private static final int COOKIE_PAIR_COUNT = 2;


    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie parse(String value) {
        if(value == null)
            return new HttpCookie(Collections.emptyMap());

        String values[] = value.split("; ");
        Map<String, String> map = new HashMap<>();

        for (String cookie : values) {
            String[] keyValue = cookie.split("=");

            if(hasValues(keyValue)) {
                map.put(keyValue[0], keyValue[1]);
            }
        }

        return new HttpCookie(map);
    }
    private static boolean hasValues(String[] values) {
        return values.length == COOKIE_PAIR_COUNT;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }
}
