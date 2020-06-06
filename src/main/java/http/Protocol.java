package http;

import java.util.Objects;

public class Protocol {

    private final String protocol;
    private final String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String values) {
        String[] protocolAndVersion = values.split("/");
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
    }


    public String getProtocol() {
        return this.protocol;
    }
    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;

        Protocol protocol = (Protocol) obj;

        return Objects.equals(this.protocol, protocol.getProtocol()) && Objects.equals(this.version, protocol.getVersion());

    }
}
