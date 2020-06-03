package http;

import java.util.Objects;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class Protocol {

    private final String protocol;
    private final String version;

    public Protocol(String protocolAndVersion) {
        String[] protocol = protocolAndVersion.split("/");
        this.protocol = protocol[0];
        this.version = protocol[1];
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Protocol)) {
            return false;
        }

        Protocol protocol1 = (Protocol) o;

        if (!Objects.equals(protocol, protocol1.protocol)) {
            return false;
        }
        return Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
