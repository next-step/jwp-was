package http.request;

public class RequestLine {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE = 3;
    private static final String DELIMITER = " ";

    private final String origin;
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(final String line) {
        String[] tokens = line.split(DELIMITER);

        if (tokens.length != REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE) {
            throw new IllegalArgumentException("Request line format is illegal : [" + line + "]");
        }

        this.origin = line;
        this.httpMethod = HttpMethod.of(tokens[0]);
        this.path = Path.parse(tokens[1]);
        this.protocol = Protocol.parse(tokens[2]);
    }

    public static RequestLine parse(final String line) {
        return new RequestLine(line);
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

    public String getExtension() {
        return path.getExtension();
    }

    public String getQueryString() {
        return path.getQueryString();
    }

    @Override
    public String toString() {
        return origin;
    }
}
