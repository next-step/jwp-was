package http;

import lombok.Getter;

@Getter
public class RequestLine {
    private static final String REQUEST_LINE_REGEX = " ";
    private static final String PATH_REGEX = "\\?";

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private QueryString queryString;

    private RequestLine(HttpMethod httpMethod, String path, Protocol protocol, QueryString queryString) {
        this.method = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.queryString = queryString;
    }

    private RequestLine(HttpMethod httpMethod, String path, Protocol protocol) {
        this(httpMethod, path, protocol, QueryString.of(""));
    }

    public static RequestLine of(String requestLine) {
        String[] values = requestLine.split(" ");
        String[] pathValues = values[1].split(PATH_REGEX);
        Protocol protocol = Protocol.of(values[2]);

        if (pathValues.length > 1) {
            return new RequestLine(HttpMethod.valueOf(values[0]), pathValues[0], protocol, QueryString.of(pathValues[1]));
        }

        return new RequestLine(HttpMethod.valueOf(values[0]), pathValues[0], protocol);
    }
}
