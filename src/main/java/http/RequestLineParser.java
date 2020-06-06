package http;

public class RequestLineParser {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE = 3;
    private static final String DELIMITER = " ";

    private RequestLineParser() {}

    public static RequestLine parse(final String requestLineStr) {
        String[] tokens = requestLineStr.split(DELIMITER);

        if (tokens.length != REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LINE) {
            throw new IllegalArgumentException("Request line format is illegal");
        }

        HttpMethod httpMethod = HttpMethod.of(tokens[0]);
        Path path = new Path(tokens[1]);
        Protocol protocol = new Protocol(tokens[2]);

        return new RequestLine(httpMethod, path, protocol);
    }
}
