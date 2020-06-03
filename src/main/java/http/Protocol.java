package http;

public class Protocol {
    public static final String PROTOCOL_SPLITTER = "/";
    public static final int PROTOCOL_STRING_TOKEN_SIZE = 2;

    private final String protocol;
    private final String version;

    public Protocol(final String protocolStr) {
        validate(protocolStr);

        String[] tokens = protocolStr.split(PROTOCOL_SPLITTER);
        verify(tokens);

        this.protocol = tokens[0];
        this.version = tokens[1];
    }

    private void validate(String protocolStr) {
        if (protocolStr == null || protocolStr.isEmpty()) {
            throw new IllegalArgumentException("Protocol string is null or empty");
        }
    }

    private void verify(String[] tokens) {
        if (tokens.length < PROTOCOL_STRING_TOKEN_SIZE) {
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
