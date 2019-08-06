package webserver.response;

public enum HttpStatus {

    SUCCESS(200, "OK"),
    REDIRECT(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int statusCode;
    private String reason;

    HttpStatus(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public String getStatusLine() {
        return "HTTP/1.1 " + this.statusCode + " " + this.reason;
    }
}
