package webserver.http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    REDIRECT(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    SERVER_ERROR(500, "Server Error");

    String httpStatusMessage;
    int httpStatusCode;

    HttpStatus(int httpStatusCode, String httpStatusMessage) {
        this.httpStatusMessage = httpStatusMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatusMessage() {
        return httpStatusMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public static String getHttpStatusMessage(int httpStatusCode){
        return Arrays.stream(HttpStatus.values())
                .filter(filter -> filter.httpStatusCode == httpStatusCode)
                .findFirst()
                .orElse(HttpStatus.NOT_FOUND)
                .getHttpStatusMessage();
    }

}
