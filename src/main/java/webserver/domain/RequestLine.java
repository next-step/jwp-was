package webserver.domain;

import java.util.Map;

public class RequestLine {
    private static final String REQUEST_DELIMITER = " ";
    private static final String PROTOCOL_VERSION_DELIMITER = "/";
    public static final int PROTOCOL_VERSION_IDX = 2;
    public static final int HTTP_METHOD_IDX = 0;
    public static final int PATH_IDX = 1;
    public static final int PROTOCOL_IDX = 0;
    public static final int VERSION_IDX = 1;
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

    public static RequestLine create(String request) {
        String[] requestElements = request.split(REQUEST_DELIMITER);
        String[] protocolVersion = requestElements[PROTOCOL_VERSION_IDX].split(PROTOCOL_VERSION_DELIMITER);

        return new RequestLine(
                HttpMethod.from(requestElements[HTTP_METHOD_IDX]),
                Path.create(requestElements[PATH_IDX]),
                Protocol.from(protocolVersion[PROTOCOL_IDX]),
                new Version(protocolVersion[VERSION_IDX])
        );
    }

    public String httpMethod() {
        return httpMethod.name();
    }

    public String index() {
        return path.index();
    }
    public Map<String, String> requestParams() {
        return path.requestParams();
    }

    public String protocol() {
        return protocol.name();
    }

    public String getVersion() {
        return version.toString();
    }
}
