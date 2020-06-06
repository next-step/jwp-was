package http.request;

import java.util.Objects;

public class Protocol {

    private final static String PROTOCOL_DELIMITER = "/";
    private final static int PROTOCOL_INDEX = 0;
    private final static int VERSION_INDEX = 1;

    private String protocol;
    private String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String value) {
        String[] values = value.split(PROTOCOL_DELIMITER);
        this.protocol = values[PROTOCOL_INDEX];
        this.version = values[VERSION_INDEX];
    }

    public String getProtocol() {
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
