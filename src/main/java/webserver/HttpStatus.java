package webserver;

import com.sun.javafx.binding.StringFormatter;

public enum HttpStatus {
    SUCCESS(200, "OK"),
    REDIRECT(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private int statusCode;
    private String reason;

    HttpStatus(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public String output() {
        return "HTTP/1.1 " + this.statusCode + " " + this.reason;
    }
}
