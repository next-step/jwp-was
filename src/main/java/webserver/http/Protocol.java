package webserver.http;

import java.util.Objects;

public class Protocol {

    private final String protocol;
    private final Version version;

    public Protocol(String value) {
        final String[] values = value.split("/");
        this.protocol = values[0];
        this.version = Version.from(values[1]);
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = Version.from(version);
    }

    public String getProtocol() {
        return this.protocol;
    }

    public Version getVersion() {
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
        return Objects.equals(getProtocol(), protocol1.getProtocol()) &&
                Objects.equals(getVersion(), protocol1.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProtocol(), getVersion());
    }
}
