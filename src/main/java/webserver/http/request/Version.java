package webserver.http.request;

import java.util.Objects;

public class Version {
    private final String value;

    public Version(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return value.equals(version.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
