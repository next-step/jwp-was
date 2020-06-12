package http;

import com.google.common.collect.Maps;
import http.Const.HttpConst;

import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private Map<String, String> cookieMap = Maps.newHashMap();

    public Cookie() {
        this.cookieMap = Maps.newHashMap();
    }

    public void parseCookie(String value) {
        String[] cookies = value.split(HttpConst.COOKIE_VALUE_SEPARATOR);

        for (String cookie : cookies) {
            String[] values = cookie.split(HttpConst.NAME_VALUE_SEPARATOR);

            if (values.length < 2) {
                throw new IllegalArgumentException("Invalid Cookie Value");
            }

            cookieMap.put(values[0], values[1]);
        }
    }

    public boolean containsCookieValue(String name) {
        return this.cookieMap.containsKey(name);
    }

    public String getCookieValue(String name) {
        return this.cookieMap.get(name);
    }

    public void addCookieValue(String name, String value) {
        this.cookieMap.put(name, value);
    }

    public String writeCookieValue() {
        return this.cookieMap.keySet()
                .stream()
                .map(key -> String.format("Set-Cookie : %s=%s %s", key, this.cookieMap.get(key), HttpConst.CRLF))
                .collect(Collectors.joining());
    }
}
