package webserver.http;

import utils.HttpRequestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookies {

    private static final String COOKIES_DELIMETER = ";";
    private static final String COOKIES_VALUES_DELIMETER = "=";
    private static final String EMPTY_COOKIE_VALUE = "";

    private Map<String, String> cookies = new HashMap<>();

    public Cookies(String cookieHeader) {
        String[] splitCookies = HttpRequestUtils.split(cookieHeader, COOKIES_DELIMETER);

        Arrays.stream(splitCookies)
                .forEach(splitCookie -> injectCookie(HttpRequestUtils.split(splitCookie, COOKIES_VALUES_DELIMETER)));
    }

    private void injectCookie(String[] cookie) {
        if (cookie.length == 2) {
            cookies.put(cookie[0].trim(), cookie[1].trim());
        }
    }

    public String getCookie(String cookieKey) {
        return cookies.getOrDefault(cookieKey, EMPTY_COOKIE_VALUE);
    }
}
