package webserver.http;

public class Cookie {
    private static final String EMPTY_STRING = "";

    private String key;
    private String value;

    public Cookie() {
        this(EMPTY_STRING, EMPTY_STRING);
    }

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        if (!key.equals(cookie.key)) return false;
        return value.equals(cookie.value);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
