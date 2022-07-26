package webserver.enums;

import static exception.ExceptionStrings.INVALID_PROTOCOL_VERSION_STRING;

public enum RequestProtocol {
    HTTP_1_1("HTTP", "1.1");

    private static final String PROTOCOL_DELIMITER = "/";
    private static final String VERSION_DOT = "\\.";
    private static final int PROTOCOL_COMPONENT_COUNT = 2;
    private static final String UNDER_BAR = "_";

    private final String protocol;
    private final String version;

    RequestProtocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public static RequestProtocol of(String protocolString) {
        if (protocolString.split(PROTOCOL_DELIMITER).length < PROTOCOL_COMPONENT_COUNT) {
            throw new IllegalArgumentException(INVALID_PROTOCOL_VERSION_STRING);
        }

        return RequestProtocol.valueOf(protocolString
            .replaceAll(PROTOCOL_DELIMITER, UNDER_BAR)
            .replaceAll(VERSION_DOT, UNDER_BAR)
        );
    }

}
