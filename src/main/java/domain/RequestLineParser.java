package domain;

public class RequestLineParser {
    public static final String VALIDATION_MESSAGE = "HTTP 요청이 형식에 맞지 않습니다.";
    private static final String REQUEST_DELIMITER = " ";
    private static final int CORRECT_LENGTH = 3;

    public HttpRequest parse(String requestLine) {
        final String[] requestSpecs = requestLine.split(REQUEST_DELIMITER);
        validate(requestSpecs);

        return new HttpRequest(
                HttpMethod.valueOf(requestSpecs[0]),
                new HttpPath(requestSpecs[1]),
                new HttpProtocol(requestSpecs[2])
        );
    }

    private void validate(String[] requestSpecs) {
        if (requestSpecs.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }
}
