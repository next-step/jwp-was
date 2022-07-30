package webserver.http.request.header;

import java.util.Map;

import webserver.http.request.method.HttpMethod;

public class RequestLine {
    private static final String REQUEST_DELIMITER = " ";
    private static final String PROTOCOL_VERSION_DELIMITER = "/";
    private static final int PROTOCOL_VERSION_IDX = 2;
    private static final int HTTP_METHOD_IDX = 0;
    private static final int PATH_IDX = 1;
    private static final int PROTOCOL_IDX = 0;
    private static final int VERSION_IDX = 1;
    private static final String DEFAULT_HTML = "/index.html";
    private static final String EMPTY_PATH = "/";
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;
    private final Version version;

    private RequestLine(HttpMethod httpMethod, Path path, Protocol protocol, Version version) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    static RequestLine create(String request) {
        String[] requestElements = request.split(REQUEST_DELIMITER);
        String[] protocolVersion = requestElements[PROTOCOL_VERSION_IDX].split(PROTOCOL_VERSION_DELIMITER);

        return new RequestLine(
                HttpMethod.from(requestElements[HTTP_METHOD_IDX]),
                Path.create(requestElements[PATH_IDX]),
                Protocol.from(protocolVersion[PROTOCOL_IDX]),
                new Version(protocolVersion[VERSION_IDX])
        );
    }

    String httpMethod() {
        return httpMethod.name();
    }

    String index() {
        if (path.index().equals(EMPTY_PATH)) {
            return DEFAULT_HTML;
        }
        return path.index();
    }

    Map<String, String> requestParams() {
        return path.requestParams();
    }

    String protocol() {
        return protocol.name();
    }

    String version() {
        return version.getVersion();
    }
}
