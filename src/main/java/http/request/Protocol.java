package http.request;

import lombok.Getter;

import java.util.*;

@Getter
public class Protocol {
    private static final String HTTP = "HTTP";
    private static final Set<String> ALLOWED_HTTP_VERSION = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1.0", "1.1")));
    private static final String VER_1_1 = "1.1";

    private String protocol;
    private String version;

    public Protocol(String protocolAndVersion) {

    }

    public Protocol(String protocol, String version) {
        if (!HTTP.equals(protocol) || !ALLOWED_HTTP_VERSION.contains(version)) {
            throw new IllegalArgumentException();
        }

        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
            Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
