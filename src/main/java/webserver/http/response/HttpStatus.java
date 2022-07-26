package webserver.http.response;

/**
 * HTTP 상태코드 RFC7231 스펙: https://datatracker.ietf.org/doc/html/rfc7231#section-6.3
 */
public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String description;

    HttpStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String fullStatusCode() {
        return this.code + " " + this.description;
    }
}
