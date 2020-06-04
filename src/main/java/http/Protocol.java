package http;

import utils.Tokens;

public class Protocol {
    public static final String PROTOCOL_SPLITTER = "/";
    public static final int PROTOCOL_STRING_TOKEN_SIZE = 2;

    private final String protocol;
    private final String version;

    public Protocol(final String protocolStr) {
        Tokens tokens = Tokens.init(protocolStr, PROTOCOL_SPLITTER);
        tokens.validate(PROTOCOL_STRING_TOKEN_SIZE);

        this.protocol = tokens.nextToken();
        this.version = tokens.nextToken();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
