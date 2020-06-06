package http.response;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
