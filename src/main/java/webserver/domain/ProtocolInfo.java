package webserver.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ProtocolInfo {

    private static final String PROTOCOL_DELIMITER = "/";

    private static final int PROTOCOL_NAME_IDX = 0;
    private static final int PROTOCOL_VERSION_IDX = 1;

    private static final int PROTOCOL_INFO_PLACE_IDX = 2;

    private String version;
    private String protocol;

    public ProtocolInfo(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static ProtocolInfo parse(String protocol) {
        String[] protocolInfo = protocol.split(PROTOCOL_DELIMITER);

        return new ProtocolInfo(protocolInfo[PROTOCOL_NAME_IDX], protocolInfo[PROTOCOL_VERSION_IDX]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolInfo that = (ProtocolInfo) o;
        return Objects.equals(version, that.version) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, protocol);
    }
}
