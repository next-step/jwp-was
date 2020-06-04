package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;

public class Protocol {

    private static final String PROTOCOL_DELIMITER = "/";
    private static final int TOKEN_SIZE = 2;

    private final String name;
    private final String version;

    public Protocol(String protocol) {
        String[] tokens = protocol.split(PROTOCOL_DELIMITER);
        if (tokens.length != TOKEN_SIZE) {
            throw new IllegalRequestLineParsingException();
        }

        this.name = tokens[0];
        this.version = tokens[1];
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
