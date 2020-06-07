package http;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cookies {
    private List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public static Cookies createCookies(String cookiesLine) {
        String[] cookies = cookiesLine.split("; ");

        return new Cookies(Arrays.stream(cookies)
                .map(Cookie::new)
                .collect(Collectors.toList()));
    }

    public Optional<Cookie> getValue(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.exists(name))
                .findFirst();
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
