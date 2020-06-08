package http.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Cookies {
    private static final String REGEX_COOKIES_DELIMITER = "&";

    private List<Cookie> cookies = new ArrayList<>();

    public Cookies(List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    public int getSize() {
        return cookies.size();
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public static Cookies parseCookies(String value) {
        if (!value.contains(REGEX_COOKIES_DELIMITER)) {
            return new Cookies(Arrays.asList(Cookie.parse(value)));
        }

        String[] cookies = value.split(REGEX_COOKIES_DELIMITER);
        return Arrays.stream(cookies)
                .map(Cookie::parse)
                .collect(collectingAndThen(toList(), Cookies::new));
    }

    @Override
    public String toString() {
        return cookies.stream()
                .map(Cookie::toString)
                .collect(Collectors.joining(REGEX_COOKIES_DELIMITER));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookies cookies1 = (Cookies) o;
        return Objects.equals(cookies, cookies1.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }
}
