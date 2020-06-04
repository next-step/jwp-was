package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProtocolVersion {

    private static final String VERSION_REGEX = "[0-9][.][0-9]";
    private final String version;

    ProtocolVersion(String version) { // TODO: 2020/06/04 public?
        if (Pattern.matches(VERSION_REGEX, version)) { // TODO: 2020/06/04 test is broken now.
            this.version = version;
        }

        throw new IllegalRequestLineParsingException();
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion that = (ProtocolVersion) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }
}
