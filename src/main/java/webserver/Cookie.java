package webserver;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Cookie {

    private Map<String, String> cookies = Maps.newHashMap();
    private static final String EQUALS = "=";
    private static final String SEMICOLON = "; ";

    public Cookie() {
        this.cookies = Maps.newHashMap();
    }

    public Cookie(String cookieString) {
        this.cookies = parsing(cookieString);
    }

    public Map<String, String> parsing(String cookieString) {
        if (cookieString == null) {
            return Maps.newHashMap();
        }

        for (String cookieArr : cookieString.split(SEMICOLON)) {
            String[] keyValue = cookieArr.split(EQUALS);
            this.cookies.put(keyValue[0], keyValue[1]);
        }
        return cookies;
    }

    /**
     * 로그인 성공 시 쿠키 저장
     */
    public Cookie setCookie(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Set<String> keySet() {
        return cookies.keySet();
    }
}
