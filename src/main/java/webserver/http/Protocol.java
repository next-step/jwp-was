package webserver.http;

import java.util.Objects;

public class Protocol {
    public static final String PROTOCOL_SPLIT_SYMBOL = "/";
    private final String protocol;

    private final String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String request) {
        String[] splitRequest = request.split(PROTOCOL_SPLIT_SYMBOL);
        this.protocol = splitRequest[0];
        this.version = splitRequest[1];
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
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
