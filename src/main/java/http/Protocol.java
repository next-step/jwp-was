package http;

import utils.Token;

public class Protocol {
    public static final String PROTOCOL_SPLITTER = "/";
    public static final int PROTOCOL_STRING_TOKEN_SIZE = 2;

    private final String protocol;
    private final String version;

    public Protocol(final String protocolStr) {
        Token token = Token.init(protocolStr, PROTOCOL_SPLITTER);
        token.validate(PROTOCOL_STRING_TOKEN_SIZE);

        this.protocol = token.nextToken();
        this.version = token.nextToken();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
