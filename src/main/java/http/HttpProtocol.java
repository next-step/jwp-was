package http;


import utils.StringUtils;


import java.util.Arrays;

public enum HttpProtocol {

    HTTP_V1_0("HTTP", "1.0"),
    HTTP_V1_1("HTTP", "1.1"),
    HTTP_V2_0("HTTP", "2.0");

    private static final String SLASH_DELIMITER = "/";
    public static final String PROTOCOL_IS_UNSUPPORTED = "protocol is unsupported.";

    private final String type;
    private final String version;

    HttpProtocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public static HttpProtocol from(String fullProtocol) {
        String[] values = StringUtils.splitIntoPair(fullProtocol, SLASH_DELIMITER);
        return HttpProtocol.of(values[0], values[1]);
    }

    public static HttpProtocol of(String protocol, String version) {
        return Arrays.stream(HttpProtocol.values())
                .filter(v -> v.type.equals(protocol) && v.version.equals(version))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(protocol + SLASH_DELIMITER + version + PROTOCOL_IS_UNSUPPORTED));
    }

    public String toJoinedString() {
        return this.type + SLASH_DELIMITER + this.version;
    }
}
