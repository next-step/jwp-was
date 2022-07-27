package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Objects;

import static model.Constant.PROTOCOL_AND_VALUE_SPERATOR;

public class Protocol {
    private final String protocol;
    private final Version version;

    public Protocol(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidRequestException("Protocol");
        }
        String[] protocols = value.split(PROTOCOL_AND_VALUE_SPERATOR);
        this.protocol = protocols[0];
        this.version = new Version(protocols[1]);
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = new Version(version);
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
