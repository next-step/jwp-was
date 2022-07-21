package webserver;

import java.util.Objects;

public class HttpProtocolSchema {
    private final static String PROTOCOL_AND_VERSION_DELIMITER = "/";
    private final static int PROTOCOL_AND_VERSION_SCHEMA_SIZE = 2;
    private final static int PROTOCOL_SCHEMA_INDEX = 0;
    private final static int VERSION_SCHEMA_INDEX = 1;
    private final HttpProtocol protocol;
    private final HttpVersion version;

    public HttpProtocolSchema(String rawHttpProtocol) {
        String[] httpProtocolSchemas = rawHttpProtocol.split(PROTOCOL_AND_VERSION_DELIMITER);
        validateHttpProtocolSchemas(httpProtocolSchemas);
        this.protocol = HttpProtocol.of(httpProtocolSchemas[PROTOCOL_SCHEMA_INDEX]);
        this.version = HttpVersion.of(httpProtocolSchemas[VERSION_SCHEMA_INDEX]);
    }

    private void validateHttpProtocolSchemas(String[] httpProtocolSchemas) {
        if (httpProtocolSchemas == null || httpProtocolSchemas.length != PROTOCOL_AND_VERSION_SCHEMA_SIZE) {
            throw new IllegalArgumentException("잘못된 HTTP 프로토콜입니다.");
        }
    }

    public HttpProtocol getProtocol() {
        return protocol;
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpProtocolSchema that = (HttpProtocolSchema) o;

        if (!Objects.equals(protocol, that.protocol)) return false;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
