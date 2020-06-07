package http.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestCookie {
    private static final String COOKIES_REGEX = ";";
    private static final String COOKIES_VALUE_REGEX = "=";
    private static final String LOGINED_KEY = "logined";

    private Map<String, String> cookieMap;

    private RequestCookie(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static RequestCookie getInstance(String value) {
        Map<String, String> cookieMap = new HashMap<>();

        String[] cookies = value.split(COOKIES_REGEX);
        for (String cookie : cookies) {
            String[] cookieValue = cookie.split(COOKIES_VALUE_REGEX);

            cookieMap.put(cookieValue[0].trim(), cookieValue[1]);
        }

        return new RequestCookie(cookieMap);
    }

    public String get(String key) {
        return cookieMap.get(key);
    }

    public boolean loggedIn() {
        return Boolean.parseBoolean(cookieMap.get(LOGINED_KEY));
    }
}
