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
    private static final String EQUAL = "=";
    public static final String JSESSIONID = "JSESSIONID";

    private List<Cookie> cookies = new ArrayList<>();


    public Cookies() {
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

    protected void addCookieByRawString(String rawCookie) {
        this.cookies.addAll(parse(rawCookie));
    }

    private List<Cookie> parse(String rawCookie) {
        return Arrays.stream(rawCookie.split(SEMICOLON))
                .map(it -> it.split(EQUAL))
                .map(it -> new Cookie(it[0].trim(), it[1].trim()))
                .collect(Collectors.toList());
    }

    public boolean hasCookie() {
        return cookies.size() > 0;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
