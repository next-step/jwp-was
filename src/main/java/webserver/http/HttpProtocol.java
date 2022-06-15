package webserver.http;

import org.apache.logging.log4j.util.Strings;

public class HttpProtocol {

    private static final String DELIMITER = "/";
    private static final int INDEX_OF_PROTOCOL = 0;
    private static final int INDEX_OF_VERSION = 1;

    private final String protocol;
    private final String version;

    public HttpProtocol(final String httpProtocol) {
        validateHttpProtocol(httpProtocol);
        final var splitHttpProtocol = httpProtocol.split(DELIMITER);
        this.protocol = splitHttpProtocol[INDEX_OF_PROTOCOL];
        this.version = splitHttpProtocol[INDEX_OF_VERSION];
    }

    private void validateHttpProtocol(String httpProtocol) {
        if (Strings.isBlank(httpProtocol)) {
            throw new IllegalArgumentException("HTTP Protocol은 필수입니다.");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
