package model.http;

import java.util.Objects;

public class Version {
    private final String version;

    public Version(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version1 = (Version) o;
        return Objects.equals(version, version1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }
}
