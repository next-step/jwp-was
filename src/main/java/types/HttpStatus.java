package types;

public enum HttpStatus {

    OK(200, "OK"),
    FOUND(302, "FOUND");

    private int code;
    private String description;

    HttpStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
}
