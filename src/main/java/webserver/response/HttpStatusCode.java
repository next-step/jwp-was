package webserver.response;

public enum HttpStatusCode {

    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request");

    private int code;
    private String status;

    HttpStatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.join(" ", String.valueOf(code), status);
    }
}
