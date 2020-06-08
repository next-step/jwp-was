package http;

public enum StatusCode {
    OK(200, "OK"),
    REDIRECT(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int statusCode;
    private final String statusText;

    StatusCode(final int statusCode, final String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public boolean isOK() {
        return this == OK;
    }

    public int getCodeValue() {
        return statusCode;
    }

    public String getResponseLine() {
        return "HTTP/1.1 " + statusCode + " " + statusText;
    }
}
