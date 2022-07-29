package webserver.http;

public class HttpProtocol {
    private static final String PROTOCOL_SPLIT_REGEX = "/";
    private final Protocol protocol;
    private final Version version;

    public HttpProtocol(String protocol) {
        validate(protocol);
        String[] strs = protocol.split(PROTOCOL_SPLIT_REGEX);
        this.protocol = Protocol.valueOf(strs[0]);
        this.version = Version.fromString(strs[1]);
    }

    private void validate(String protocol) {
        if (!protocol.contains(PROTOCOL_SPLIT_REGEX)) {
            throw new IllegalArgumentException("invalid protocol: " + protocol);
        }
    }
}
