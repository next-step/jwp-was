package request;


public class RequestLine {

    private static final String DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    private final HttpMethod httpMethod;
    private final Path path;
    private final ProtocolVersion protocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(DELIMITER);
        httpMethod = HttpMethod.from(splitRequestLine[HTTP_METHOD_INDEX]);
        path = new Path(splitRequestLine[PATH_INDEX]);
        protocol = new ProtocolVersion(splitRequestLine[PROTOCOL_INDEX]);
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getPath() {
        return path.getName();
    }

    public String getPathValueOf(String key) {
        return path.getValue(key);
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }
}
