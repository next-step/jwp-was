package http.request.protocol;

public class Protocol {

    private final static String DELIMITER = "/";

    private ProtocolType protocolType;
    private String version;

    private Protocol(ProtocolType protocolType, String version) {
        this.protocolType = protocolType;
        this.version = version;
    }

    public static Protocol from(String protocol) {
        String[] protocolSplits = protocol.split(DELIMITER);
        validateProtocol(protocolSplits[0]);

        return new Protocol(
                ProtocolType.valueOf(protocolSplits[0]),
                protocolSplits[1]
        );
    }

    private static void validateProtocol(String str) {
        if (!str.equals(str.toUpperCase())) {
            throw new IllegalArgumentException("프로토콜은 대문자여야 합니다.");
        }
    }

    public String getProtocolType() {
        return protocolType.name();
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.join(DELIMITER, String.valueOf(protocolType), version);
    }
}
