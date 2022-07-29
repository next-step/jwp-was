package webserver.request;

import java.util.Arrays;

public enum Protocol {

    HTTP_1_1("HTTP", "1.1"),
    HTTP_2_0("HTTP", "2.0");

    private static final String PROTOCOL_DELIMITER = "/";

    private final String type;
    private final String version;

    Protocol(final String type, final String version) {
        this.type = type;
        this.version = version;
    }

    public static Protocol parse(final String protocol) {
        return Arrays.stream(values())
            .filter(it -> it.value().equals(protocol))
            .findFirst()
            .orElse(HTTP_1_1);
    }

    public String value() {
        return type + PROTOCOL_DELIMITER + version;
    }

}
