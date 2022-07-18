package webserver;

import java.util.Objects;

public class HttpProtocol {
    private static final String PROTOCOL_DELIMITER = "/";

    private static final String PROTOCOL_NAME = "HTTP";

    private static final int PROTOCOL_VALUES_LENGTH = 2;

    private static final int NAME_IDX = 0;

    private static final int VERSION_IDX = 1;

    private final String name;

    private final String version;

    HttpProtocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    static HttpProtocol parseOf(String httpProtocol) {
        String[] protocolValues = httpProtocol.split(PROTOCOL_DELIMITER);

        if (protocolValues.length != PROTOCOL_VALUES_LENGTH && !protocolValues[NAME_IDX].equals(PROTOCOL_NAME)) {
            throw new IllegalArgumentException(String.format("Protocol 파싱 실패 httpProtocol:[%s]", httpProtocol));
        }

        return new HttpProtocol(protocolValues[NAME_IDX], protocolValues[VERSION_IDX]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpProtocol protocol = (HttpProtocol) o;
        return Objects.equals(name, protocol.name) && Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }
}
