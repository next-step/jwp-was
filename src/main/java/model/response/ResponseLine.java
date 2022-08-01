package model.response;

import enums.HttpStatus;
import model.WebProtocol;

public class ResponseLine {
    private final HttpStatus httpStatus;
    private final WebProtocol webProtocol;

    public ResponseLine(HttpStatus httpStatus, WebProtocol webProtocol) {
        this.httpStatus = httpStatus;
        this.webProtocol = webProtocol;
    }

    public String getResponseLine() {
        return webProtocol.getWebProtocol() + " " + HttpStatus.getHttpCode(httpStatus) + " " + httpStatus.name();
    }
}
