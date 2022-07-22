package http.httprequest.requestline;

import java.util.Objects;

public class Protocol {
    public static final String PROTOCOL_DELIMITER = "/";
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;

    private final ProtocolType protocolType;
    private final Version version;

    public Protocol(ProtocolType protocolType,
                    Version version) {
        this.protocolType = protocolType;
        this.version = version;
    }

    public static Protocol from(String value) {
        String[] values = value.split(PROTOCOL_DELIMITER);
        return new Protocol(ProtocolType.valueOf(values[PROTOCOL_INDEX]), new Version(values[VERSION_INDEX]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return protocolType.equals(protocol1.protocolType) && version.equals(protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocolType, version);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "protocolType=" + protocolType +
                ", version=" + version +
                '}';
    }
}
