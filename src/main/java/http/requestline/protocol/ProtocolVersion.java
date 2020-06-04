package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProtocolVersion {

    private final String version;

    ProtocolVersion(String version) {
        if (isIllegalPattern(version)) {
            throw new IllegalRequestLineParsingException();
        }

        this.version = version;
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

    private boolean isIllegalPattern(String version) {
        return !ProtocolVersionMatcher.match(version);
    }

    public static class ProtocolVersionMatcher {

        private static final String VERSION_REGEX = "^[0-9][.][0-9]$";

        private ProtocolVersionMatcher() {
        }

        public static boolean match(String version) {
            return Pattern.matches(VERSION_REGEX, version);
        }
    }
}
