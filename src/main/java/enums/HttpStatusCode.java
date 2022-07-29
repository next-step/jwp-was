package enums;

public enum HttpStatusCode {
    OK(200);

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
