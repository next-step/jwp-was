package webserver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {

    public static final String COOKIE = "Cookie";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE_PATH = "Path";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_IDX = 0;
    private static final int VALUE_IDX = 1;
    private Map<String, String> cookieData;

    public Cookie() {
    }

    public Cookie(Map<String, String> cookieData) {
        this.cookieData = cookieData;
    }

    public static Cookie parse(String cookieString) {
        String[] datas = cookieString.split(COOKIE_DELIMITER);
        System.out.println("datas = " + datas);
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
    public void setCookie(String key, String value) {
        cookieData.put(key, value);
    }


    private String setCookie(String bool) {
        return "Set-Cookie: " + "logined=" + bool + "; " + "Path" + "=" + "/";
    }
}
