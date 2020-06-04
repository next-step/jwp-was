package http;

import utils.Token;

public class RequestLineParser {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LIEN = 3;
    private static final String DELIMITER = " ";

    public static RequestLine parse(final String requestLineStr) {
        Token token = Token.init(requestLineStr, DELIMITER);
        token.validate(REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LIEN);

        String method = token.nextToken();
        Path path = new Path(token.nextToken());
        Protocol protocol = new Protocol(token.nextToken());

        return new RequestLine(method, path, protocol);
    }
}
