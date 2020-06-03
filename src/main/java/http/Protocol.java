package http;

public class Protocol {
    public static final String PROTOCOL_SPLITTER = "/";

    private final String protocol;
    private final String version;

    public Protocol(final String protocolStr) {
        String[] tokens = protocolStr.split(PROTOCOL_SPLITTER);

        validate(tokens);

        this.protocol = tokens[0];
        this.version = tokens[1];
    }

    private void validate(String[] tokens) {
        if (tokens.length < 2) {
            throw new IllegalArgumentException();
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
