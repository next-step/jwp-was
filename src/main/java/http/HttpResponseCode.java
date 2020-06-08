package http;

public enum HttpResponseCode {
    OK(200),
    FOUND(302),
    NOT_FOUNT(404);

    private int code;

    HttpResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
