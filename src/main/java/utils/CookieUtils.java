package utils;

import http.Cookie;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CookieUtils {

    public static Set<Cookie> stringToCookies(String cookiesString) {
        return Arrays.stream(cookiesString.split("; "))
                .map(cookieString -> cookieString.split("="))
                .map(nameValue -> new Cookie(nameValue[0], nameValue[1]))
                .collect(Collectors.toSet());
    }
}
