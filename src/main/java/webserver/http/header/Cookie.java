package webserver.http.header;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String EMPTY_STRING = "";
    private static final String COOKIES_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int ELEMENT_COUNT = 1;

    private Map<String, String> cookies;

    public Cookie(Map<String, String> cookies) {
        validateCookies(cookies);
        this.cookies = cookies;
    }

    public void setCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public static Cookie parse(String cookieString) {
        validateCookieString(cookieString);
        String[] multiCookies = cookieString.split(COOKIES_DELIMITER);
        Map<String, String> cookies = new HashMap<>();
        Arrays.stream(multiCookies).filter(cookie -> !cookie.isEmpty()).forEach(cookie -> setCookies(cookies, cookie));
        return new Cookie(cookies);
    }

    private static void setCookies(Map<String, String> cookies, String cookie) {
        String[] cookieElements = cookie.split(COOKIE_KEY_VALUE_DELIMITER);
        if (cookieElements.length == ELEMENT_COUNT) {
            cookies.put(cookieElements[KEY_INDEX], EMPTY_STRING);
            return;
        }
        cookies.put(cookieElements[KEY_INDEX], cookieElements[VALUE_INDEX]);
    }

    private static void validateCookieString(String cookieString) {
        if (cookieString == null) {
            throw new IllegalArgumentException("요청된 HTTP Header 의 cookie 는 null 일 수 없습니다.");
        }
    }

    private void validateCookies(Map<String, String> cookies) {
        if (cookies == null) {
            throw new IllegalArgumentException("cookies 값이 null 일 수 없습니다.");
        }
    }

    public Cookie() {
        this(new HashMap<>());
    }

    public String getValue(String key) {
        return this.cookies.getOrDefault(key, EMPTY_STRING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        return cookies.equals(cookie.cookies);
    }

    @Override
    public int hashCode() {
        return cookies.hashCode();
    }
}
