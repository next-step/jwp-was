package exception;

import http.response.HttpStatusCode;

public class NotFoundException extends RuntimeException {

    private final String message = "해당 값을 찾지 못했습니다.";

    public NotFoundException(HttpStatusCode statusCode) {
        super(statusCode.toString());
    }
}
