package webserver;

public class Protocol {
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP 요청 ";
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;
    public static final int PROTOCOL_INFORMATION_SIZE = 2;
    public static final String PROTOCOL_DELIMITER = "/";
    private String protocol;
    private Double version;

    public Protocol(String protocol, Double version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String protocolInformation) {
        final String[] items = protocolInformation.split(PROTOCOL_DELIMITER);
        if (items.length != PROTOCOL_INFORMATION_SIZE) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST);
        }
        this.protocol = items[PROTOCOL_INDEX];
        this.version = Double.valueOf(items[VERSION_INDEX]);
    }

    public String getProtocol() {
        return protocol;
    }

    public Double getVersion() {
        return version;
    }
}
