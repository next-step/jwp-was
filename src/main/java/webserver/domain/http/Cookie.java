package webserver.domain.http;

import java.util.Objects;

public class Cookie {

    public static String COOKIE_KEY_NAME = "Cookie";
    private final String name;
    private final String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) && Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
