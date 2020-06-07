package http;

import lombok.Getter;

@Getter
public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method not Allowed");

    private int code;
    private String name;

    HttpStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
