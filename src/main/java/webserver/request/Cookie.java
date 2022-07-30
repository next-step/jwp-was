package webserver.request;

import java.util.Objects;

public class Cookie {

    private static final String COOKIE_DELIMITER = "=";
    private final String name;
    private final String value;

    public Cookie(final String name, final String value) {
        validateCookieName(name);
        this.name = name;
        this.value = value;
    }

    public static Cookie parse(final String nameAndValueFair) {
        validateCookieNameAndValueFair(nameAndValueFair);
        final String[] nameAndValueArray = nameAndValueFair.split(COOKIE_DELIMITER);
        return new Cookie(nameAndValueArray[0].trim(), nameAndValueArray[1].trim());
    }

    private static void validateCookieNameAndValueFair(final String nameAndValueFair) {
        if (nameAndValueFair == null || nameAndValueFair.isBlank() || nameAndValueFair.split(COOKIE_DELIMITER).length != 2) {
            throw new IllegalArgumentException("쿠키 유형이 올바르지 않습니다. : " + nameAndValueFair);
        }
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
