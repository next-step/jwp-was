package http;

import java.util.Objects;
import java.util.Set;

public class Cookie {

    private final String key;
    private final String value;
    private final Set<String> options;

    public Cookie(String key, String value) {
        this(key, value, Set.of());
    }

    public Cookie(String key, String value, Set<String> options) {
        this.key = key;
        this.value = value;
        this.options = options;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Set<String> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cookie cookie = (Cookie)o;
        return Objects.equals(key, cookie.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public boolean isEquals(String key) {
        return this.key.equals(key);
    }
}
