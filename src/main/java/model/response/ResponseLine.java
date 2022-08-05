package model.response;

import enums.HttpStatus;
import model.WebProtocol;

public class ResponseLine {
    private final HttpStatus httpStatus;
    private final WebProtocol webProtocol;

    private ResponseLine(HttpStatus httpStatus, WebProtocol webProtocol) {
        this.httpStatus = httpStatus;
        this.webProtocol = webProtocol;
    }

    public static ResponseLine httpOk() {
        return new ResponseLine(HttpStatus.OK, new WebProtocol("HTTP", "1.1"));
    }

    public static ResponseLine httpFound() {
        return new ResponseLine(HttpStatus.FOUND, new WebProtocol("HTTP", "1.1"));
    }

    public static ResponseLine httpBadRequest() {
        return new ResponseLine(HttpStatus.BAD_REQUEST, new WebProtocol("HTTP", "1.1"));
    }

    public String getResponseLine() {
        return webProtocol.getWebProtocol() + " " + HttpStatus.getHttpCode(httpStatus) + " " + httpStatus.name();
    }
}
