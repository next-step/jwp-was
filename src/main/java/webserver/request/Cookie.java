package webserver.request;

import java.util.Objects;

public class Cookie {

    private final String name;
    private final String value;

    public Cookie(final String name, final String value) {
        validateCookieName(name);
        this.name = name;
        this.value = value;
    }

    private void validateCookieName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠키 이름은 비어 있을 수 없습니다");
        }
    }

    public boolean isSameName(final String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) && Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

}
