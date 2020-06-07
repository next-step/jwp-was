package http;

import java.util.Objects;

public class Cookie {
    private String name;
    private String value;

    public Cookie(String cookieLine) {
        String[] nameAndValue = cookieLine.split("=");
        if (nameAndValue.length != 2) {
            throw new IllegalArgumentException("쿠키 포맷을 다시 한번 확인해주세요.");
        }

        this.name = nameAndValue[0];
        this.value = nameAndValue[1];
    }

    public boolean exists(String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
