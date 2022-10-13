package webserver.http.request;

import utils.StringUtils;
import webserver.http.session.SessionManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {

    private static final String COOKIE_HEADER_DELIMITER = ": ";
    private static final String COOKIE_DELIMITER = ";";
    private static final String COOKIE_VALUE_DELIMITER = "=";
    private static final String LOGINED = "logined";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> cookies;

    private Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookies empty() {
        return new Cookies(new HashMap<>());
    }

    public static Cookies parse(String cookie) {
        if (StringUtils.isNullAndBlank(cookie)) {
            return empty();
        }

        String[] cookieValues = segregateCookieValue(cookie).split(COOKIE_DELIMITER);

        Map<String, String> cookiesMap = Arrays.stream(cookieValues)
                .map(it -> it.split(COOKIE_VALUE_DELIMITER))
                .collect(Collectors.toMap(e -> e[KEY].trim(), e -> e[VALUE].trim()));
        return new Cookies(cookiesMap);
    }

    private static String segregateCookieValue(String cookie) {
        String[] cookieValue = cookie.split(COOKIE_HEADER_DELIMITER);
        int valueIndex = cookieValue.length - 1;
        return cookieValue[valueIndex];
    }

    public boolean hasSignIn() {
        return cookies.containsKey(SessionManager.SESSION_KEY);
    }

    public String getCookieValue(String cookieKey) {
        return cookies.get(cookieKey);
    }

    public int getCookiesSize() {
        return cookies.size();
    }
}
