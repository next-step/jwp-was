package http;

import utils.RequestParseUtils;


import java.util.Arrays;

public enum Protocol {

    HTTP_V1_0("HTTP", "1.0"),
    HTTP_V1_1("HTTP", "1.1"),
    HTTP_V2_0("HTTP", "2.0");

    private static final String SLASH_DELIMITER = "/";

    private final String type;
    private final String version;

    Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public static Protocol from(String fullProtocol) {
        String[] splittedProtocol = RequestParseUtils.splitIntoPair(fullProtocol, SLASH_DELIMITER);
        return Protocol.of(splittedProtocol[0], splittedProtocol[1]);
    }

    public static Protocol of(String protocol, String version) {
        return Arrays.stream(Protocol.values())
                .filter(v -> v.type.equals(protocol) && v.version.equals(version))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported Protocol."));
    }
}
