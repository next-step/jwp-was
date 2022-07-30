package webserver.request;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cookies {

    private static final String COOKIES_DELIMITER = ";";
    private final List<Cookie> values = new ArrayList<>();

    public Cookies() {
    }

    public Cookies(final List<Cookie> values) {
        this.values.addAll(values);
    }

    public boolean hasCookie(final String findCookieName) {
        return values.stream()
            .anyMatch(cookie -> cookie.isSameName(findCookieName));
    }

    public void addCookie(final Cookie cookie) {
        values.add(cookie);
    }

    public void addCookies(final String values) {
        Arrays.stream(values.split(COOKIES_DELIMITER))
            .map(Cookie::parse)
            .collect(toList())
            .forEach(this::addCookie);
    }

    public String getCookie(final String cookieName) {
        return values.stream()
            .filter(cookie -> cookie.isSameName(cookieName))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cookies cookies = (Cookies) o;
        return Objects.equals(values, cookies.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
