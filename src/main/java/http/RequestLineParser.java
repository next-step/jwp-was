package http;

import http.exception.IllegalRequestLineParsingException;

public class RequestLineParser {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int TOKEN_SIZE = 3;

    private RequestLineParser() {
    }

    public static RequestLine parse(String request) {
        String[] tokens = request.split(REQUEST_LINE_DELIMITER);
        if (tokens.length != TOKEN_SIZE) {
            throw new IllegalRequestLineParsingException();
        }

        String method = tokens[0];
        String url = tokens[1];
        String protocol = tokens[2];

        return new RequestLine(method, url, protocol);
    }
}
