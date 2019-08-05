package webserver.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2019-08-05.
 */
public class Cookies {
    private static final String SEMICOLON = ";";
    public static final String EQUAL = "=";

    private List<Cookie> cookies = new ArrayList<>();


    public Cookies() {
    }

    private Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies parse(String rawCookie) {
        List<Cookie> cookies = Arrays.stream(rawCookie.split(SEMICOLON))
                .map(it -> it.split(EQUAL))
                .map(it -> new Cookie(it[0].trim(), it[1].trim()))
                .collect(Collectors.toList());

        return new Cookies(cookies);
    }

    public String getCookie(String name) {
        return cookies.stream()
                .filter(it -> it.isEqualName(name))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public boolean hasCookie() {
        return cookies.size() > 0;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
