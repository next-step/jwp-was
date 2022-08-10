package webserver.http.model.response;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "FOUND");

    private final int code;
    private final String status;

    StatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public String statusCodeText() {
        return this.code + " " + this.status;
    }
}
