package http.request;

import java.util.Objects;

public class Protocol {
    public static final String PROTOCOL_SPLITTER = "/";

    private final String protocol;
    private final String version;

    public Protocol(final String protocol, final String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol parse(final String protocol) {
        validate(protocol);

        String[] tokens = protocol.split(PROTOCOL_SPLITTER);

        if (tokens.length != 2) {
            throw new IllegalArgumentException("Protocol and version string is not illegal");
        }

        return new Protocol(tokens[0], tokens[1]);
    }

    private static void validate(String protocolStr) {
        if (Objects.isNull(protocolStr)) {
            throw new IllegalArgumentException("Protocol string is null");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
