package webserver.http.domain;

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

    public boolean hasValue(String value) {
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
