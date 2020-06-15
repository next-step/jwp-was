package http;

import com.google.common.collect.Maps;
import http.Const.HttpConst;

import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private Map<String, String> cookieValues;

    public Cookie() {
        this.cookieValues = Maps.newHashMap();
    }

    public void parseCookie(String value) {
        String[] cookies = value.split(HttpConst.COOKIE_VALUE_SEPARATOR);

        for (String cookie : cookies) {
            String[] values = cookie.split(HttpConst.NAME_VALUE_SEPARATOR);

            if (values.length < 2) {
                throw new IllegalArgumentException("Invalid Cookie Value");
            }

            cookieValues.put(values[0], values[1]);
        }
    }

    public boolean containsCookieValue(String name) {
        return this.cookieValues.containsKey(name);
    }

    public String getCookieValue(String name) {
        return this.cookieValues.get(name);
    }

    public void addCookieValue(String name, String value) {
        this.cookieValues.put(name, value);
    }

    public Map<String,String> getCookieValues() {
        return this.cookieValues;
    }
}
