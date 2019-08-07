package webserver.http.response;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String description;

    HttpStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return this.statusCode + " " + this.description;
    }

    public static HttpStatus lookup(int statusCode) {
        return Arrays.stream(HttpStatus.values())
                .filter(httpStatus -> httpStatus.getStatusCode() == statusCode)
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}