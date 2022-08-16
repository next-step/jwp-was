package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

public class Version {
    private final String version;

    public Version(String version) {
        validateVersion(version);

        this.version = version;
    }

    private void validateVersion(String version) {
        if (StringUtils.isEmpty(version) || !StringUtils.contains(version, ".")) {
            throw new InvalidRequestException("version");
        }
    }

    public String getVersion() {
        return version;
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
