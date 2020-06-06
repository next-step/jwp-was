package http;

public class RequestLine {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE = 3;
    private static final String DELIMITER = " ";

    private final String origin;
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(final String requestLineStr) {
        String[] tokens = requestLineStr.split(DELIMITER);

        if (tokens.length != REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE) {
            throw new IllegalArgumentException("Request line format is illegal : [" + requestLineStr + "]");
        }

        this.origin = requestLineStr;
        this.httpMethod = HttpMethod.of(tokens[0]);
        this.path = new Path(tokens[1]);
        this.protocol = new Protocol(tokens[2]);
    }

    public void addParameter(final String token) {
        path.addParameter(token);
    }

    public static RequestLine init(final String requestLineStr) {
        return new RequestLine(requestLineStr);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getParameter(final String parameter) {
        return path.getParameter(parameter);
    }

    public String getExtension() {
        return path.getExtension();
    }

    @Override
    public String toString() {
        return origin;
    }
}
