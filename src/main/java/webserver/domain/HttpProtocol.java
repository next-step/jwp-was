package webserver.domain;

import java.util.Objects;

public class HttpProtocol {
    private static final String PROTOCOL_DELIMITER = "/";
    private static final int HTTP_PROTOCOL_LENGTH = 2;

    private final String protocol;
    private final String version;

    public HttpProtocol(String httpProtocol) {
        String[] protocols = httpProtocol.split(PROTOCOL_DELIMITER);
        validate(protocols);
        this.protocol = protocols[0];
        this.version = protocols[1];
    }

    private void validate(String[] split) {
        if (split.length != HTTP_PROTOCOL_LENGTH) {
            throw new IllegalArgumentException("HttpProtocol 형식이 올바르지 않습니다.");
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
        return Objects.equals(getProtocol(), that.getProtocol()) && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProtocol(), getVersion());
    }
}
