package http;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.jknack.handlebars.internal.lang3.BooleanUtils.negate;

public class HTTPProtocol {
    private static final List<String> HTTP_PROTOCOLS = Arrays.asList("HTTP", "HTTPS");

    public static final int PROTOCOL_INDEX = 0;
    public static final int PROTOCOL_VERSION_INDEX = 1;

    private String protocol;
    private Double version;

    public HTTPProtocol(String value) {
        String[] protocolAndVersion = value.split("/");
        validateHTTPProtocolInfo(protocolAndVersion);

        this.protocol = protocolAndVersion[0];
        this.version = Double.parseDouble(protocolAndVersion[1]);
    }

    private void validateHTTPProtocolInfo(String[] protocolAndVersion) {
        if (protocolAndVersion.length != 2) {
            throw new IllegalArgumentException("프로토콜 정보를 다시 한번 확인 부탁드립니다.");
        }

        if (negate(HTTP_PROTOCOLS.contains(protocolAndVersion[PROTOCOL_INDEX]))) {
            throw new IllegalArgumentException("잘못된 프로토콜을 사용하셨습니다.");
        }

        try {
            Double.parseDouble(protocolAndVersion[PROTOCOL_VERSION_INDEX]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 프로토콜 버전을 사용하셨습니다.", e);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public Double getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HTTPProtocol HTTPProtocol1 = (HTTPProtocol) o;
        return Objects.equals(protocol, HTTPProtocol1.protocol) &&
                Objects.equals(version, HTTPProtocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
