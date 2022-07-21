package webserver.request;

import java.util.Objects;

public class Protocol {
    private String protocol;

    public Protocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "protocol='" + protocol + '\'' +
                '}';
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
}
