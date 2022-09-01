package webserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {

    public static final String COOKIE = "Cookie";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE_PATH = "Path";
    public static final String COOKIE_DELIMITER = "; ";
    public static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_IDX = 0;
    private static final int VALUE_IDX = 1;
    private Map<String, String> cookieData = new HashMap<>();

    public Cookie() {
    }

    public Cookie(Map<String, String> cookieData) {
        this.cookieData = cookieData;
    }

    public static Cookie parse(String cookieString) {
        String[] datas = cookieString.split(COOKIE_DELIMITER);
        Map<String, String> data = Arrays.stream(datas)
                .map(cookie -> (cookie.split(KEY_VALUE_DELIMITER)))
                .collect(Collectors.toMap(s -> s[KEY_IDX], s -> s[VALUE_IDX]));
        return new Cookie(data);
    }

    public String getValue(String key) {
        return cookieData.get(key);
    }

    public String getCookiePath() {
        return cookieData.get(Cookie.COOKIE_PATH);

    }
    public void setCookie(String value, HttpHeader header) {
        this.cookieData.put(SET_COOKIE, value);
        header.putCookie(this.cookieData);
    }

    public HttpSession createSession() {
        return new HttpSession();
    }
}
