package http.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import utils.StringUtils;

import java.util.Collections;
import java.util.Map;

public class Cookies {
    public static final String SESSION_ID_COOKIE_NAME = "JSESSIONID";

    public static final String COOKIE_DELIMITER = "; ";
    public static final String COOKIE_KEY_VALUE_SEPARATOR = "=";

    private final Map<String, String> cookies;

    public static Cookies parse(String cookieHeader) {
        if (StringUtils.isEmpty(cookieHeader)) {
            return new Cookies(Collections.emptyMap());
        }


        return new Cookies(splitCookies(cookieHeader));
    }

    private static Map<String, String> splitCookies(String cookieHeader) {
        return Splitter.on(COOKIE_DELIMITER)
            .omitEmptyStrings()
            .withKeyValueSeparator(COOKIE_KEY_VALUE_SEPARATOR)
            .split(cookieHeader);
    }

    public Cookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    @Override
    public String toString() {
        return Joiner.on(COOKIE_DELIMITER)
                .withKeyValueSeparator(COOKIE_KEY_VALUE_SEPARATOR)
                .join(cookies);
    }
}
