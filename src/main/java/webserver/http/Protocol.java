package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

public class Protocol {
    public static final String PROTOCOL_AND_VALUE_SEPARATOR = "/";

    private final String protocol;
    private final Version version;

    public Protocol(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidRequestException("Protocol");
        }
        String[] protocols = value.split(PROTOCOL_AND_VALUE_SEPARATOR);
        this.protocol = protocols[0];
        this.version = new Version(protocols[1]);
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = new Version(version);
    }

    public String getProtocolAndVersion() {
        return protocol + PROTOCOL_AND_VALUE_SEPARATOR + version.getVersion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) && Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
