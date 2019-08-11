package webserver.http;

import utils.ParsingUtils;
import utils.StringUtils;

import java.util.Collections;
import java.util.Map;

public class Cookies {

    private Map<String, String> cookies;

    public Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }

    public static Cookies parse(String value) {
        return StringUtils.isNotBlank(value) ?
                new Cookies(ParsingUtils.parseCookie(value)) : new Cookies(Collections.emptyMap());
    }

    public String getCookie(String key) {
        return cookies.getOrDefault(key, null);
    }
}
