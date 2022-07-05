package webserver.http;

public class HttpProtocol {

    private static final String URI_SEPARATOR = "/";
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private final String protocol;

    private final String version;


    public HttpProtocol(final String protocol) {

        final String[] splitProtocol = protocol.split(URI_SEPARATOR);
        protocolValidate(splitProtocol);

        this.protocol = splitProtocol[PROTOCOL_INDEX];
        this.version = splitProtocol[VERSION_INDEX];
    }

    private void protocolValidate(final String[] splitProtocol) {
        if(splitProtocol.length <= 1) {
            throw new IllegalArgumentException("잘못된 Protocol 정보 입니다.");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.join("/", protocol, version);
    }
}
