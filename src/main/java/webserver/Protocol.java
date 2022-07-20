package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

public class Protocol {
    private final String protocol;

    public Protocol(String protocol) {
        if (StringUtils.isEmpty(protocol)) {
            throw new InvalidRequestException("Protocol");
        }
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "protocol='" + protocol + '\'' +
                '}';
    }
}
