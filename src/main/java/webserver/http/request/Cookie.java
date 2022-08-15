package webserver.http.request;


import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {

    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY_VALUE = "";

    public static final String LOGINED = "logined";

    private final Map<String, String> cookies;

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie parseFrom(String rawCookies) {
        if (rawCookies.isEmpty() || rawCookies.isBlank()) {
            return new Cookie(Collections.EMPTY_MAP);
        }

        String[] splitCookies = rawCookies.split(COOKIE_DELIMITER);

        return new Cookie(
                Arrays.stream(splitCookies)
                        .map(cookie -> cookie.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]))
        );
    }

    public static Cookie emptyCookie() {
        return new Cookie(Collections.EMPTY_MAP);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
