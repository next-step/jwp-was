package http.cookie;

import http.cookie.exception.IllegalCookieHeaderFormatException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static http.cookie.Cookie.COOKIE_DELIMITER;
import static http.cookie.Cookie.COOKIE_VALUE_DELIMITER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieHeaderParser {

    public static List<Cookie> parse(String value) {
        List<Cookie> cookies = new ArrayList<>();

        String[] cookieTokens = value.split(COOKIE_DELIMITER);
        for (String cookieToken : cookieTokens) {
            Cookie cookie = createCookie(cookieToken);
            cookies.add(cookie);
        }
        return cookies;
    }

    private static Cookie createCookie(String cookieToken) {
        if (!cookieToken.contains(COOKIE_VALUE_DELIMITER)) {
            throw new IllegalCookieHeaderFormatException();
        }

        String[] tokens = cookieToken.split(COOKIE_VALUE_DELIMITER);
        return new Cookie(tokens[0], tokens[1]);
    }
}
