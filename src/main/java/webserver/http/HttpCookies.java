package webserver.http;

import utils.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HttpCookies {

    public static final String COOKIES_SPLIT_SIGN = ";";

    public static final String COOKIE_SPLIT_SIGN = "=";
    public static final int COOKIE_SPLIT_LIMIT = 2;

    private Map<String, HttpCookie> cookies;

    private HttpCookies(Map<String, HttpCookie> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookies of(String cookieValues) {

        List<String> cookieSplitValues = Optional.ofNullable(cookieValues)
                .map(values -> values.split(COOKIES_SPLIT_SIGN))
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

        Map<String, HttpCookie> cookies = cookieSplitValues.stream()
                .filter(value -> !StringUtils.isEmpty(value))
                .map(HttpCookies::parseHttpCookie)
                .collect(Collectors.toMap(httpCookie -> httpCookie.getName(), Function.identity(), (c1, c2) -> c1));

        return new HttpCookies(cookies);
    }

    private static HttpCookie parseHttpCookie(String cookieValue) {
        String[] cookieNameAndValue = cookieValue.split(COOKIE_SPLIT_SIGN, COOKIE_SPLIT_LIMIT);

        if(cookieNameAndValue.length != COOKIE_SPLIT_LIMIT) {
            throw new IllegalArgumentException("cookie value 이상함");
        }

        return new HttpCookie(cookieNameAndValue[0].trim(), cookieNameAndValue[1].trim());
    }

    public HttpCookie getCookie(String cookieName) {
        return cookies.get(cookieName);
    }
}
