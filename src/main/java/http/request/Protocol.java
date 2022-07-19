package http.request;

public class Protocol {

    private final static String DELIMITER = "/";

    private ProtocolType protocolType;
    private Double version;

    private Protocol(ProtocolType protocolType, Double version) {
        this.protocolType = protocolType;
        this.version = version;
    }

    public static Protocol of(String protocol) {
        String[] protocolSplits = protocol.split(DELIMITER);
        validateProtocol(protocolSplits[0]);

        return new Protocol(
                ProtocolType.valueOf(protocolSplits[0]),
                Double.parseDouble(protocolSplits[1])
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

    public Double getVersion() {
        return version;
    }
}
