package http;

import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import utils.Args;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class Cookies {

    protected static final String ILLEGAL_COOKIE = "유효하지 않은 Cookie 입니다.";
    private static final String COOKIE_DELIMITER = ";";
    private static final String COOKIE_NAME_VALUE_DELIMITER = "=";
    private static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> cookieJar = Maps.newHashMap();
    private String path = Strings.EMPTY;

    public Cookies() {
    }

    public Cookies(String line) {
        if (line == null) {
            return;
        }

        for (String cookie : line.split(COOKIE_DELIMITER)) {
            String[] values = cookie.trim().split(COOKIE_NAME_VALUE_DELIMITER, -1);
            Args.check(values.length == 2, ILLEGAL_COOKIE);
            cookieJar.put(values[0].trim(), values[1].trim());
        }
    }

    public String getCookie(String cookieName) {
        return cookieJar.get(cookieName);
    }

    public void addCookie(String key, String value) {
        cookieJar.put(key, value);
    }

    public void addCookiePath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        if (!cookieJar.isEmpty()) {
            StringBuffer sb = new StringBuffer();

            sb.append(HttpHeaderNames.SET_COOKIE).append(HEADER_DELIMITER);
            for (String cookie : cookieJar.keySet()) {
                sb.append(cookie).append(COOKIE_NAME_VALUE_DELIMITER).append(cookieJar.get(cookie)).append(COOKIE_DELIMITER);
            }
            if (!Strings.isEmpty(path)) {
                sb.append("Path=").append(path);
            }
            return sb.toString();
        }
        return Strings.EMPTY;
    }

    public boolean isEmpty() {
        return cookieJar.isEmpty();
    }
}
