package webserver;

public class RequestLine {

    private static final String EMPTY_STRING = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int REQUEST_URL_INDEX = 1;
    private static final int PROTOCOL_VERSION_INDEX = 2;

    private HttpMethod httpMethod;
    private Path path;
    private ProtocolVersion protocolVersion;
    private RequestParamMap requestParamMap;

    protected RequestLine() {

    }

    private RequestLine(
            final HttpMethod httpMethod,
            final Path path,
            final ProtocolVersion protocolVersion,
            final RequestParamMap requestParamMap
    ) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.requestParamMap = requestParamMap;
    }

    public static RequestLine from(final String startLine) {
        String[] splitStartLine = startLine.split(EMPTY_STRING);
        HttpMethod httpMethod = HttpMethod.from(splitStartLine[HTTP_METHOD_INDEX]);
        Path path = Path.from(splitStartLine[REQUEST_URL_INDEX]);
        ProtocolVersion protocolVersion = ProtocolVersion.from(splitStartLine[PROTOCOL_VERSION_INDEX]);
        RequestParamMap requestParamMap = RequestParamMap.from(splitStartLine[REQUEST_URL_INDEX]);

        return new RequestLine(httpMethod, path, protocolVersion, requestParamMap);
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public Path getPath() {
        return this.path;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.protocolVersion;
    }

    public RequestParamMap getRequestParamMap() {
        return this.requestParamMap;
    }
}
