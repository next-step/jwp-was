package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static utils.DelimiterConstants.COOKIE_VALUE_DELIMITER;
import static utils.DelimiterConstants.PARAMETER_KEY_VALUE_DELIMITER;

public class HttpCookie {
    private static final String JESSIONID = "JESSIONID";

    private final Map<String, Object> cookieMap;

    private HttpCookie() {
        cookieMap = new HashMap<>();
    }

    private HttpCookie(Map<String, Object> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static HttpCookie of(String cookies) {
        Map<String, Object> map = new HashMap<>();
        String[] split = cookies.split(COOKIE_VALUE_DELIMITER);
        for (String cookie : split) {
            String[] splitCookie = cookie.split(PARAMETER_KEY_VALUE_DELIMITER);
            map.put(splitCookie[0], splitCookie[1]);
        }
        return new HttpCookie(map);
    }

    public static HttpCookie empty() {
        return new HttpCookie();
    }

    public boolean contains(String value) {
        return cookieMap.keySet()
                .stream()
                .anyMatch(cookie -> cookie.contains(value));
    }

    public String cookieList() {
        return cookieMap.entrySet().stream()
                .map(entry -> entry.getKey() + PARAMETER_KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_VALUE_DELIMITER));
    }

    public void add(String cookieKey, Object cookieValue) {
        this.cookieMap.put(cookieKey, cookieValue);
    }

    public String getSessionId() {
        String id = (String) cookieMap.get(JESSIONID);
        if (id == null) {
            return UUID.randomUUID().toString();
        }
        return id;
    }
}
