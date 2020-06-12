package http.common;

import com.google.common.base.Splitter;

import java.util.Map;

public class Cookies {
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
    public static final String COOKIE_HEADER_NAME = "Cookie";
    public static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";
    public static final String LOGIN_SUCCESS_COOKIE_VALUE = "logined=true";
    public static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false";

    public static final String COOKIE_SPLITTER = ";";
    public static final String COOKIE_KEY_VALUE_SEPARATOR = "=";

    private final Map<String, String> cookies;

    public static Cookies parse(String cookieHeader) {
        return new Cookies(getSplitCookies(cookieHeader));
    }

    private static Map<String, String> getSplitCookies(String cookieHeader) {
        return Splitter.on(COOKIE_SPLITTER)
            .omitEmptyStrings()
            .withKeyValueSeparator(Splitter.on(COOKIE_KEY_VALUE_SEPARATOR).limit(2))
            .split(cookieHeader);
    }

    public Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }
}
