package webserver;

import webserver.response.ResponseHolder;

import java.io.DataOutputStream;
import java.io.IOException;

public enum StatusCode {

    OK(200, "OK"),
    FOUND(302, "Found") {
        @Override
        public void handleResponse(ResponseHolder responseHolder) throws IOException {
            super.handleResponse(responseHolder);

        }
    },
    NOT_FOUND(404, "Not Found"),
    ;

    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void handleResponse(ResponseHolder responseHolder) throws IOException {
        responseHolder.getDos()
                .writeBytes("HTTP/1.1 " + this.code + " " + this.message + " \r\n");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
