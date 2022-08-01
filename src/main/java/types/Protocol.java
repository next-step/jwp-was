package types;

import exception.ProtocolNotFoundException;

import java.util.Arrays;

public enum Protocol {

    HTTP_1_0("HTTP", "1.0"), HTTP_1_1("HTTP", "1.1"), HTTP_2("HTTP", "2.0"), HTTP_3("HTTP", "3.0");

    private static final String PROTOCOL_VERSION_SEPARATOR = "/";
    private final String name;
    private final String version;

    Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public static Protocol find(String requestProtocol) {
        if (!requestProtocol.contains(PROTOCOL_VERSION_SEPARATOR)) {
            throw new ProtocolNotFoundException();
        }

        String protocolName = requestProtocol.split(PROTOCOL_VERSION_SEPARATOR)[0];
        String protocolVersion = requestProtocol.split(PROTOCOL_VERSION_SEPARATOR)[1];

        return Arrays.stream(Protocol.values())
                .filter(protocol -> protocolName.equals(protocol.name))
                .filter(protocol -> protocolVersion.equals(protocol.version))
                .findAny()
                .orElseThrow(ProtocolNotFoundException::new);
    }

    public String getInfo() {
        return this.name + PROTOCOL_VERSION_SEPARATOR + this.version;
    }

}
