package http.requestline;

import http.requestline.exception.IllegalRequestLineParsingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestLineParser {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int TOKEN_SIZE = 3;

    public static RequestLine parse(String request) {
        String[] tokens = request.split(REQUEST_LINE_DELIMITER);
        if (tokens.length != TOKEN_SIZE) {
            throw new IllegalRequestLineParsingException();
        }

        String method = tokens[0];
        String path = tokens[1];
        String protocolSpec = tokens[2];

        return RequestLine.builder()
                .method(method)
                .path(path)
                .protocolSpec(protocolSpec)
                .build();
    }
}
