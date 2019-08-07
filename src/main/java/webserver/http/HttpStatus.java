package webserver.http;

public enum HttpStatus {
    OK(200, "ok"),
    REDIRECT(302, "Redirect"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private int statusCode;
    private String action;

    HttpStatus(int statusCode, String action) {
        this.statusCode = statusCode;
        this.action = action;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getAction() {
        return action;
    }
}
