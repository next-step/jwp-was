package webserver.domain;

import static exception.ExceptionStrings.INVALID_COOKIE_KEY;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;

public class Cookies {
    private static final String COOKIE_STRING_SPLIT_REGEX = ";";
    private static final String COOKIE_KEY_VALUE_SPLIT_REGEX = "=";

    private Map<String, String> map;

    private Cookies(Map<String, String> map) {
        this.map = map;
    }

    public static Cookies of(String cookieString) {
        Map<String, String> map = Maps.newHashMap();

        String[] cookieStrings = cookieString.split(COOKIE_STRING_SPLIT_REGEX);
        for (String cookieKeyVal : cookieStrings) {
            String[] keyVal = cookieKeyVal.trim().split(COOKIE_KEY_VALUE_SPLIT_REGEX);
            map.put(keyVal[0].trim(), keyVal[1].trim());
        }

        return new Cookies(map);
    }

    public static Cookies empty() {
        return new Cookies(Maps.newHashMap());
    }

    public String get(String key) {
        if (Strings.isNullOrEmpty(key)) {
            throw new IllegalArgumentException(INVALID_COOKIE_KEY);
        }
        return map.getOrDefault(key, "");
    }
}
