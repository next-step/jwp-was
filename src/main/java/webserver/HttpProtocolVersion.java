package webserver;

public class HttpProtocolVersion {

    private static final String PROTOCOL_SEPERATOR = "/";
    private String protocol;
    private String version;

    public HttpProtocolVersion(String protocolVersion) {
        String[] splitProtocolVersion = protocolVersion.split(PROTOCOL_SEPERATOR);
        if (splitProtocolVersion.length != 2) {
            throw new IllegalArgumentException("잘못된 HTTP 프로토콜 형식입니다.");
        }
        this.protocol = splitProtocolVersion[0];
        this.version = splitProtocolVersion[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
