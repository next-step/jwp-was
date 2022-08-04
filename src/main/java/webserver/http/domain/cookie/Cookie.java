package webserver.http.domain.cookie;

import java.util.Objects;

public class Cookie {
    private static final String DEFAULT_PATH = "/";

    private final String name;
    private final String value;
    private final String path;

    public Cookie(String name, String value) {
        this(name, value, DEFAULT_PATH);
    }

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public static Cookie of(String name, boolean value) {
        return new Cookie(name, String.valueOf(value), DEFAULT_PATH);
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value, DEFAULT_PATH);
    }

    public boolean equalsValue(String value) {
        return Objects.equals(value, this.value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }
}
