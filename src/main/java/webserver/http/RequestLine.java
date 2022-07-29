package webserver.http;

public class RequestLine {
    private static final int VALID_WHITESPACE_COUNT = 3;
    private static final String REQUESTLINE_SPLIT_REGEX = " ";

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        String[] strs = requestLine.split(REQUESTLINE_SPLIT_REGEX);
        validate(strs);

        this.httpMethod = HttpMethod.valueOf(strs[0]);
        this.httpPath = new HttpPath(strs[1]);
        this.httpProtocol = new HttpProtocol(strs[2]);
    }

    private void validate(String[] strings) {
        if (strings.length != VALID_WHITESPACE_COUNT) {
            throw new IllegalArgumentException();
        }
    }
}
