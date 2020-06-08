package http.request;

import java.util.Map;

public class RequestLine {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE = 3;
    private static final String DELIMITER = " ";

    private final String origin;
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(final String requestLine) {
        String[] tokens = requestLine.split(DELIMITER);

        if (tokens.length != REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE) {
            throw new IllegalArgumentException("Request line format is illegal : [" + requestLine + "]");
        }

        this.origin = requestLine;
        this.httpMethod = HttpMethod.of(tokens[0]);
        this.path = Path.parse(tokens[1]);
        this.protocol = Protocol.parse(tokens[2]);
    }

    public void addParameter(final String token) {
        path.addParameter(token);
    }

    public static RequestLine parse(final String requestLine) {
        return new RequestLine(requestLine);
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

    public Map<String, String> getParameters() {
        return path.getParameters();
    }
}
