package http;

import utils.Tokens;

public class RequestLineParser {
    private static final int REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LIEN = 3;
    private static final String DELIMITER = " ";

    public static RequestLine parse(final String requestLineStr) {
        Tokens tokens = Tokens.init(requestLineStr, DELIMITER);
        tokens.validate(REQUIRED_TOKEN_SIZE_TO_INIT_REQUEST_LIEN);

        HttpMethod httpMethod = HttpMethod.of(tokens.nextToken());
        Path path = new Path(tokens.nextToken());
        Protocol protocol = new Protocol(tokens.nextToken());

        return new RequestLine(httpMethod, path, protocol);
    }
}
