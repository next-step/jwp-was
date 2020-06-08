package http;

public enum HttpStatus {

    _200(200, "OK"),
    _302(302, "FOUND");

    private int code;
    private String reason;

    HttpStatus(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return code + " " + reason;
    }
}
