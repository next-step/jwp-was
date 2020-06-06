package http;

import java.util.Objects;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(final String value) {
        String[] protocolAndVersion = value.split("/");
        if(protocolAndVersion.length != 2){
            throw new IllegalArgumentException();
        }
        protocol = protocolAndVersion[0];
        version = protocolAndVersion[1];
    }

    public Protocol(final String protocol, final String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String getProtocol(){
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
                Objects.equals(version, protocol1.version);
    }
}