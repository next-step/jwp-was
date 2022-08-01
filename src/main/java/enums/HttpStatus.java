package enums;

import java.util.Arrays;

public enum HttpStatus {
    OK(200),
    FOUND(302),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_ERROR(500),
    BAD_GATEWAY(502),
    GATEWAY_TIMEOUT(504);

    private final int httpCode;

    HttpStatus(int httpCode) {
        this.httpCode = httpCode;
    }

    public static int getHttpCode(HttpStatus httpStatus) {
        return Arrays
            .stream(HttpStatus.values())
            .filter(it -> it.httpCode == httpStatus.httpCode)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .getHttpCode();
    }

    private int getHttpCode() {
        return this.httpCode;
    }
}
