package http;

public enum HttpStatus {

    OK(200, "OK"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request");

    private int code;
    private String value;

    HttpStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCodeValue(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.getCode());
    }

    public String getValue(HttpStatus httpStatus) {
        return httpStatus.getValue();
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
