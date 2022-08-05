package webserver.http.response.statusline;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");


    private final int statusCode;
    private final String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String statusMessage() {
        return this.statusMessage;
    }
}
