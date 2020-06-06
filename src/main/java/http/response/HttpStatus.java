package http.response;

import java.util.Arrays;

public enum HttpStatus {
    OK(200),
    REDIRECT(302);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
