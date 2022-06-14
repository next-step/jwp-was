package webserver.request;

import java.util.Objects;

public class Protocol {
    private static final String SLASH = "/";
    private final String type;
    private final String version;

    private Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public static Protocol from(String protocolStr) {
        String[] protocolSplit = protocolStr.split(SLASH);
        return new Protocol(protocolSplit[0], protocolSplit[1]);
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protocol)) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(getType(), protocol.getType()) &&
                Objects.equals(getVersion(), protocol.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getVersion());
    }
}
