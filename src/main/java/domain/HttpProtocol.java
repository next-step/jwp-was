package domain;

import java.util.Objects;

public class HttpProtocol {
    public static final String VALIDATION_MESSAGE = "프로토콜이 형식에 맞지 않습니다.";
    private static final String PROTOCOL_DELIMITER = "/";
    private static final int CORRECT_LENGTH = 2;

    private final String protocol;
    private final String version;

    public HttpProtocol(String protocolSpec) {
        final String[] splitProtocolSpec = protocolSpec.split(PROTOCOL_DELIMITER);
        validate(splitProtocolSpec);

        this.protocol = splitProtocolSpec[0];
        this.version = splitProtocolSpec[1];
    }

    private void validate(String[] splitProtocolSpec) {
        if (splitProtocolSpec.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpProtocol that = (HttpProtocol) o;
        return Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }

    @Override
    public String toString() {
        return "HttpProtocol{" +
                "protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
